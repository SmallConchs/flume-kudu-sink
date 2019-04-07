/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdata.jdd.kudu.flumeng.sink.bo;

import com.google.gson.Gson;

/**
 * @author wrj
 */
public class TestMain {

    public static void main(String[] args) {

        String raw = "{\"database\":\"uic1\",\"table\":\"uic_user\",\"type\":\"update\",\"ts\":1529558525,\"xid\":521418558,\"commit\":true,\"data\":"
                + "{\"id\":1658368,\"loginname\":\"f8578bdb27284b5e9651a5dec6a484db\",\"password\":\"78b539375ec547118ec11faede0acb2e\",\"nickname\":\"ok_590164665726\""
                + ",\"phone\":null,\"head_img\":\"/group1/M00/07/31/CmcEHFsrNf2AALDTAAAZVII0x7U318.png\",\"user_source\":5,\"third_id\":\"12d252c3f64d4c46d8e73470d29651b0\""
                + ",\"third_head_img\":\"http://www.okooo.com/Upload/user/024/38/09/56_avatar_middle.jpg\",\"third_params\":null,\"sound_flag\":null,\"status\":1,\"visitor_token\":null"
                + ",\"reg_channel_id\":100006,\"reg_parent_channel\":100006,\"last_login_channel_id\":100006,\"create_time\":\"2018-06-21 13:22:06\""
                + ",\"update_time\":\"2018-06-21 13:22:05\",\"delete_flag\":0,\"invitation_code\":null,\"parent_invitation_code\":null,\"back_award_percent\":null"
                + ",\"source_nickname\":\"ok_590164665726\",\"system_code\":2},\"old\":{\"source_nickname\":22222,\"invitation_code\":null}}";

//        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
//        System.out.println(pp.getData().toString());
//        UicUser people = gson.fromJson(raw, new TypeToken<UicUser>() {}.getType());
//
//
        Gson gson = new Gson();
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
        System.out.println(value);

//        System.out.println(people.getData().get("id"));
//        System.out.println(people.getData().get("nickname"));
//        System.out.println(people.getData().get("user_source"));
//        System.out.println(people.getData().get("third_id"));
//        System.out.println(people.getData().get("status"));
//        System.out.println(people.getData().get("reg_channel_id"));
//        System.out.println(people.getData().get("reg_parent_channel"));
//        System.out.println(people.getData().get("last_login_channel_id"));
//        System.out.println(people.getData().get("create_time"));
//        System.out.println(people.getData().get("invitation_code"));
//        System.out.println(people.getData().get("source_nickname"));
//
//        User u = new User();
//        long id=Double.valueOf(people.getData().get("id").toString()).longValue();
//        long reg_channel_id=Double.valueOf(people.getData().get("reg_channel_id").toString()).longValue();
//        long reg_parent_channel=Double.valueOf(people.getData().get("reg_parent_channel").toString()).longValue();
//        long last_login_channel_id=Double.valueOf(people.getData().get("last_login_channel_id").toString()).longValue();
//        int status=Double.valueOf(people.getData().get("status").toString()).intValue();
//        
//        
//        u.setId(id);
//        u.setNickname(people.getData().get("nickname").toString());
//        u.setThird_id(people.getData().get("third_id").toString());
//        u.setStatus(status);
//        u.setReg_channel_id(reg_channel_id);
//        u.setReg_parent_channel(reg_parent_channel);
//        u.setLast_login_channel_id(last_login_channel_id);
//        u.setCreate_time(people.getData().get("create_time").toString());
//        u.setInvitation_code(people.getData().get("invitation_code").toString());
//        u.setSource_nickname(people.getData().get("source_nickname").toString());
//        User uu = people.getData();
//        Gson gg = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        String value = gg.toJson(u);
//        System.out.println(value);
//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(raw);
//        JsonObject jsonObj = jsonElement.getAsJsonObject();
//        System.out.println(jsonObj.get("data").toString());
//        Gson gs1 = new Gson();
//        User u = gs1.fromJson(jsonObj.get("data"), User.class);
//        System.out.println(u.toString());
    }

}
