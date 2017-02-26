package com.web.application.project.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 管理员
 * @author evans
 */
public class Admin extends Page implements Serializable{

    private Integer admin_id;//管理员id
    private String language_code;//语言代码
    private String account;//管理员账号
    private String password;//管理员密码
    private String name;//管理员姓名
    private String department;//部门
    private String comment;//备注
    private Timestamp create_time;//创建时间
    private Timestamp last_login_time;//最后登录时间
    private String is_locked;//是否锁定，y为已锁定，n为未锁定
    private String is_delete;//是否删除，y为已删除，n为未删除

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public Timestamp getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Timestamp last_login_time) {
        this.last_login_time = last_login_time;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
