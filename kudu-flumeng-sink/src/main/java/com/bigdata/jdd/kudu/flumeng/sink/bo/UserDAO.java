/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdata.jdd.kudu.flumeng.sink.bo;

/**
 * @author wrj
 */
public class UserDAO {

    private long id;
    private String loginname;
    private String password;

    private String nickname;

    private String phone;
    private String head_img;
    private int user_source;
    private String third_id;

    private String third_head_img;

    private String third_params;

    private int sound_flag;
    private int status;

    private String visitor_token;
    private long reg_channel_id;
    private long reg_parent_channel;
    private long last_login_channel_id;
    private String create_time;

    private String update_time;

    private int delete_flag;
    private String invitation_code;

    private String parent_invitation_code;

    private double back_award_percent;
    private String source_nickname;

    private int system_code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
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

    public String getThird_head_img() {
        return third_head_img;
    }

    public void setThird_head_img(String third_head_img) {
        this.third_head_img = third_head_img;
    }

    public String getThird_params() {
        return third_params;
    }

    public void setThird_params(String third_params) {
        this.third_params = third_params;
    }

    public int getSound_flag() {
        return sound_flag;
    }

    public void setSound_flag(int sound_flag) {
        this.sound_flag = sound_flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVisitor_token() {
        return visitor_token;
    }

    public void setVisitor_token(String visitor_token) {
        this.visitor_token = visitor_token;
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

    public long getLast_login_channel_id() {
        return last_login_channel_id;
    }

    public void setLast_login_channel_id(long last_login_channel_id) {
        this.last_login_channel_id = last_login_channel_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getParent_invitation_code() {
        return parent_invitation_code;
    }

    public void setParent_invitation_code(String parent_invitation_code) {
        this.parent_invitation_code = parent_invitation_code;
    }

    public double getBack_award_percent() {
        return back_award_percent;
    }

    public void setBack_award_percent(double back_award_percent) {
        this.back_award_percent = back_award_percent;
    }

    public String getSource_nickname() {
        return source_nickname;
    }

    public void setSource_nickname(String source_nickname) {
        this.source_nickname = source_nickname;
    }

    public int getSystem_code() {
        return system_code;
    }

    public void setSystem_code(int system_code) {
        this.system_code = system_code;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", loginname=" + loginname + ", password=" + password + ", nickname=" + nickname + ", phone=" + phone + ", head_img=" + head_img + ", user_source=" + user_source + ", third_id=" + third_id + ", third_head_img=" + third_head_img + ", third_params=" + third_params + ", sound_flag=" + sound_flag + ", status=" + status + ", visitor_token=" + visitor_token + ", reg_channel_id=" + reg_channel_id + ", reg_parent_channel=" + reg_parent_channel + ", last_login_channel_id=" + last_login_channel_id + ", create_time=" + create_time + ", update_time=" + update_time + ", delete_flag=" + delete_flag + ", invitation_code=" + invitation_code + ", parent_invitation_code=" + parent_invitation_code + ", back_award_percent=" + back_award_percent + ", source_nickname=" + source_nickname + ", system_code=" + system_code + '}';
    }

}
