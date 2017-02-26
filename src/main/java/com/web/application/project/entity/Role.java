package com.web.application.project.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 角色
 * @author evans
 */
public class Role extends Page implements Serializable{
    private Integer role_id;//角色id
    private String role_name;//角色名称
    private String role_flag;//角色标记
    private String is_delete;//是否删除，y为已删除，n为未删除

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getRole_flag() {
        return role_flag;
    }

    public void setRole_flag(String role_flag) {
        this.role_flag = role_flag;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
