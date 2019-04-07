/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bigdata.jdd.kudu.flumeng.sink;

import com.bigdata.jdd.kudu.flumeng.sink.bo.UicUser;
import com.bigdata.jdd.kudu.flumeng.sink.bo.User;
import com.bigdata.jdd.kudu.flumeng.sink.bo.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.Charset;
import java.util.List;
/**
 * @author wrj
 */
public class WfUicUserInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(WfUicUserInterceptor.class);
    private final Gson gson = new Gson();
    @Override
    public void initialize() {
        // no-op
    }
    /**
     * Modifies events in-place.
     */
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    public Event intercept(Event event) {
        String raw = new String(event.getBody(), charset);
        logger.info("user--->" + raw);
        try {
            UicUser people = gson.fromJson(raw, UicUser.class);
            UserDAO dao = people.getData();
            User u = new User();
            u.setInvitation_code(dao.getInvitation_code());
            u.setLogin_channel_id(dao.getLast_login_channel_id());
            u.setName(dao.getNickname());
            u.setReg_channel_id(dao.getReg_channel_id());
            u.setReg_parent_channel(dao.getReg_parent_channel());
            u.setSigup_time(dao.getCreate_time());
            u.setSource_nickname(dao.getSource_nickname());
            u.setThird_id(dao.getThird_id());
            u.setUser_id(dao.getId());
            u.setUser_source(dao.getUser_source());
            u.setUser_status(dao.getStatus());
            String value = gson.toJson(u, User.class);
            event.setBody(value.getBytes(charset));
        } catch (JsonSyntaxException e) {
            logger.error("intercept.................", e);
        }
        return event;
    }
    /**
     * Delegates to {@link #intercept(Event)} in a loop.
     *
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }
    @Override
    public void close() {
        // no-op
    }

    /**
     * Builder which builds new instances of the HostInterceptor.
     */
    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new WfUicUserInterceptor();
        }
        @Override
        public void configure(Context context) {
        }
    }
}
