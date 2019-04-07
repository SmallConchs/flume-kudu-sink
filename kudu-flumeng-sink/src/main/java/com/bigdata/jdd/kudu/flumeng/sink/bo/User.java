/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdata.jdd.kudu.flumeng.sink.bo;

/**
 * @author wrj
 */
public class User {

    private long user_id;

    private String name;

    private int user_source;
    private String third_id;

    private int user_status;

    private long reg_channel_id;
    private long reg_parent_channel;
    private long login_channel_id;
    private String sigup_time;

    private String invitation_code;
    private String source_nickname;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_source() {
        return user_source;
    }

    public void setUser_source(int user_source) {
        this.user_source = user_source;
    }

    public String getThird_id() {
        return third_id;
    }

    public void setThird_id(String third_id) {
        this.third_id = third_id;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public long getReg_channel_id() {
        return reg_channel_id;
    }

    public void setReg_channel_id(long reg_channel_id) {
        this.reg_channel_id = reg_channel_id;
    }

    public long getReg_parent_channel() {
        return reg_parent_channel;
    }

    public void setReg_parent_channel(long reg_parent_channel) {
        this.reg_parent_channel = reg_parent_channel;
    }

    public long getLogin_channel_id() {
        return login_channel_id;
    }

    public void setLogin_channel_id(long login_channel_id) {
        this.login_channel_id = login_channel_id;
    }

    public String getSigup_time() {
        return sigup_time;
    }

    public void setSigup_time(String sigup_time) {
        this.sigup_time = sigup_time;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getSource_nickname() {
        return source_nickname;
    }

    public void setSource_nickname(String source_nickname) {
        this.source_nickname = source_nickname;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", name=" + name + ", user_source=" + user_source + ", third_id=" + third_id + ", user_status=" + user_status + ", reg_channel_id=" + reg_channel_id + ", reg_parent_channel=" + reg_parent_channel + ", login_channel_id=" + login_channel_id + ", sigup_time=" + sigup_time + ", invitation_code=" + invitation_code + ", source_nickname=" + source_nickname + '}';
    }


}
