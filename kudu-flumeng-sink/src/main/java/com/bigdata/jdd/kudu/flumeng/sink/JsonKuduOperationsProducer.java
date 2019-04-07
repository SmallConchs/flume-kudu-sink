/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdata.jdd.kudu.flumeng.sink;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.kudu.ColumnSchema;
import org.apache.kudu.Schema;
import org.apache.kudu.Type;
import org.apache.kudu.client.KuduTable;
import org.apache.kudu.client.Operation;
import org.apache.kudu.client.PartialRow;
import org.apache.kudu.flume.sink.KuduOperationsProducer;
import org.apache.kudu.shaded.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author wrj
 */
public class JsonKuduOperationsProducer implements KuduOperationsProducer {

    private static final String quote = "\t";
    private static final Logger logger = LoggerFactory.getLogger(JsonKuduOperationsProducer.class);
    private static final String INSERT = "insert";
    private static final String UPSERT = "upsert";
    private static final List<String> validOperations = Lists.newArrayList(UPSERT, INSERT);

    public static final String ENCODING_PROP = "encoding";
    public static final String DEFAULT_ENCODING = "utf-8";
    public static final String OPERATION_PROP = "operation";
    public static final String DEFAULT_OPERATION = UPSERT;
    public static final String SKIP_MISSING_COLUMN_PROP = "skipMissingColumn";
    public static final boolean DEFAULT_SKIP_MISSING_COLUMN = true;
    public static final String SKIP_BAD_COLUMN_VALUE_PROP = "skipBadColumnValue";
    public static final boolean DEFAULT_SKIP_BAD_COLUMN_VALUE = true;
    public static final String WARN_UNMATCHED_ROWS_PROP = "skipUnmatchedRows";
    public static final boolean DEFAULT_WARN_UNMATCHED_ROWS = true;

    private KuduTable table;
    private Charset charset;
    private String operation;
    private boolean skipMissingColumn;
    private boolean skipBadColumnValue;
    private boolean warnUnmatchedRows;
    Schema schema = null;
    List<ColumnSchema> csList = null;
    List<ColumnSchema> tableKey = null;

    public JsonKuduOperationsProducer() {
    }

    @Override
    public void initialize(KuduTable table) {
        this.table = table;
        schema = table.getSchema();//--------表的Schema
        csList = schema.getColumns();//---------表的column list
        tableKey = schema.getPrimaryKeyColumns();//----------table的primary key
    }

    private Map<String, String> getJsonMap(String raw) {
        Map<String, String> jsonMap = Maps.newHashMap();
        try {
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            java.lang.reflect.Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            jsonMap = gson.fromJson(raw, type);
        } catch (JsonSyntaxException e) {
            jsonMap = Maps.newHashMap();
            logger.error("convert fail!--------> " + raw, e);
        }
        return jsonMap;
    }

    @Override
    public List<Operation> getOperations(Event event) throws FlumeException {
        String raw = new String(event.getBody(), charset);
//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(raw);
//        JsonObject jsonObj = jsonElement.getAsJsonObject();
        //
//        Gson gson = new GsonBuilder().create();

        Operation op;
        logger.info("2---->{}===={}", operation, raw);
        switch (operation) {
            case UPSERT:
                op = table.newUpsert();
                break;
            case INSERT:
                op = table.newInsert();
                break;
            default:
                throw new FlumeException(
                        String.format("Unrecognized operation type '%s' in getOperations(): "
                                + "this should never happen!", operation));
        }

//        logger.info("op---->{}------>{}", operation, raw);
        List<Operation> ops = Lists.newArrayList();

        Map<String, String> jsonMap = getJsonMap(raw);
        if (jsonMap.isEmpty()) {
            return ops;
        }
        Set<String> set = jsonMap.keySet();
        //check 所有的key 值都存在
        boolean keyFlag = true;
        for (ColumnSchema pk : tableKey) {
            if (pk.getName().equalsIgnoreCase("insert_uuid")) {
                continue;
            }
            keyFlag &= set.contains(pk.getName());
            if (!keyFlag) {
                logger.error("parse error key is lost----{}", pk.getName());
                break;
            }
        }
        if (!keyFlag) {
            return ops;
        }

        PartialRow row = op.getRow();
        boolean flag = true;
        boolean isLength = true;

        for (String colName : set) {
            boolean ee = exist(csList, colName);
//            logger.info("3-------------current-------->{}--------{}", colName, ee);
            isLength &= ee;
            if (!ee) {
                logger.error("parser error col is not exist " + colName + quote + raw, new FlumeException("current col name is not exist "));
                break;
            }
        }
        if (isLength) {
            for (ColumnSchema col : csList) {
//                logger.info("4------->" + col.getName() + quote + col.isKey());
                try {
                    if (!set.contains(col.getName())) {
                        continue;
                    }

                    String rawVal = jsonMap.get(col.getName());
                    if (StringUtils.isBlank(rawVal) || rawVal.equalsIgnoreCase("null")) {
                        rawVal = convertNullValue(rawVal, col.getName(), col.getType());
                    }

                    if (col.getType() == Type.STRING) {
                        if (rawVal.startsWith("\"") && rawVal.endsWith("\"")) {
                            rawVal = StringUtils.substringAfter(rawVal, "\"");
                            rawVal = StringUtils.substringBeforeLast(rawVal, "\"");
                        }
                    }
                    if (col.isKey()) {
                        if (StringUtils.isBlank(rawVal) || rawVal.equalsIgnoreCase("null")) {
                            flag = false;
                            logger.error("parser error" + col.getName() + quote + raw,
                                    new FlumeException("current row key value is null"));
                            break;
                        }
                    }
                    flag &= coerceAndSet(rawVal, col.getName(), col.getType(), row);
                    if (!flag) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    String msg = String.format("Raw value '%s' couldn't be parsed to type %s for column '%s'", raw, col.getType(), col.getName());
                    flag = false;
                    logOrThrow(skipBadColumnValue, msg, e);
                } catch (IllegalArgumentException e) {
                    String msg = String.format("Column '%s' has no matching group in '%s'", col.getName(), raw);
                    flag = false;
                    logOrThrow(skipMissingColumn, msg, e);
                } catch (Exception e) {
                    flag = false;
                    logger.error("parser error" + col.getName() + quote + raw, new FlumeException("Failed to create Kudu operation", e));
                }
            }
            flag &= coerceAndSet(UUID.randomUUID().toString(), "insert_uuid", Type.STRING, row);
            if (flag) {
                ops.add(op);
            } else {
                logger.error("op error" + quote + raw, new FlumeException("current op exception!!!"));
            }
        }

        logger.info(
                "ops size --------->{}" + ops.size());
        return ops;
    }

    private boolean exist(List<ColumnSchema> list, String colName) {
        for (ColumnSchema cs : list) {
            if (cs.getName().equalsIgnoreCase(colName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void configure(Context context) {
        String charsetName = context.getString(ENCODING_PROP, DEFAULT_ENCODING);
        try {
            charset = Charset.forName(charsetName);
        } catch (IllegalArgumentException e) {
            throw new FlumeException(
                    String.format("Invalid or unsupported charset %s", charsetName), e);
        }
        operation = context.getString(OPERATION_PROP, DEFAULT_OPERATION).toLowerCase();
        Preconditions.checkArgument(
                validOperations.contains(operation),
                "Unrecognized operation '%s'",
                operation);
        skipMissingColumn = context.getBoolean(SKIP_MISSING_COLUMN_PROP,
                DEFAULT_SKIP_MISSING_COLUMN);
        skipBadColumnValue = context.getBoolean(SKIP_BAD_COLUMN_VALUE_PROP,
                DEFAULT_SKIP_BAD_COLUMN_VALUE);
        warnUnmatchedRows = context.getBoolean(WARN_UNMATCHED_ROWS_PROP,
                DEFAULT_WARN_UNMATCHED_ROWS);

    }

    private boolean coerceAndSet(String rawVal, String colName, Type type, PartialRow row) {
        boolean flag = true;
        try {
            switch (type) {
                case INT8:
                    row.addByte(colName, Byte.parseByte(rawVal));
                    break;
                case INT16:
                    row.addShort(colName, Short.parseShort(rawVal));
                    break;
                case INT32:
                    row.addInt(colName, Integer.parseInt(rawVal));
                    break;
                case INT64:
                    row.addLong(colName, Long.parseLong(rawVal));
                    break;
                case BINARY:
                    row.addBinary(colName, rawVal.getBytes(charset));
                    break;
                case STRING:
                    row.addString(colName, rawVal);
                    break;
                case BOOL:
                    row.addBoolean(colName, Boolean.parseBoolean(rawVal));
                    break;
                case FLOAT:
                    row.addFloat(colName, Float.parseFloat(rawVal));
                    break;
                case DOUBLE:
                    row.addDouble(colName, Double.parseDouble(rawVal));
                    break;
                case UNIXTIME_MICROS:
                    row.addLong(colName, Long.parseLong(rawVal));
                    break;
                default:
                    flag = false;
                    logger.warn("got unknown type {} for column '{}'-- ignoring this column",
                            type, colName);
            }
        } catch (NumberFormatException e) {
            flag = false;
            logger.error("number format exception", e);
        }
        return flag;
    }

    private String convertNullValue(String rawVal, String colName, Type type) {
        switch (type) {
            case INT8:
                rawVal = "0";
                break;
            case INT16:
                rawVal = "0";
                break;
            case INT32:
                rawVal = "0";
                break;
            case INT64:
                rawVal = "0";
                break;
            case BINARY:
                rawVal = "0";
                break;
            case STRING:
                rawVal = "";
                break;
            case BOOL:
                rawVal = "false";
                break;
            case FLOAT:
                rawVal = "0";
                break;
            case DOUBLE:
                rawVal = "0";
                break;
            case UNIXTIME_MICROS:
                rawVal = "0";
                break;
            default:
                logger.warn("got unknown type {} for column '{}'-- ignoring this column",
                        type, colName);
        }
        return rawVal;
    }

    private void logOrThrow(boolean log, String msg, Exception e)
            throws FlumeException {
        if (log) {
            logger.error(msg, e);
        } else {
            logger.error(msg, new FlumeException(msg, e));
            throw new FlumeException(msg, e);
        }
    }

    @Override
    public void close() {
    }

}
