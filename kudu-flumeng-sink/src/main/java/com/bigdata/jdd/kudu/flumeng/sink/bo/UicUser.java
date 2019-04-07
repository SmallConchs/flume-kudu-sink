/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdata.jdd.kudu.flumeng.sink.bo;

import java.util.Map;

/**
 * @author wrj
 */
public class UicUser {

    private String database;
    private String table;
    private String type;
    private long ts;
    private long xid;
    private String commit;
    private UserDAO data;
    private Map old;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getXid() {
        return xid;
    }

    public void setXid(long xid) {
        this.xid = xid;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public UserDAO getData() {
        return data;
    }

    public void setData(UserDAO data) {
        this.data = data;
    }


    public Map getOld() {
        return old;
    }

    public void setOld(Map old) {
        this.old = old;
    }

}
