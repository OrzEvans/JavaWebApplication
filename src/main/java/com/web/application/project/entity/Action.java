package com.web.application.project.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 权限
 * @author evans
 */
public class Action extends Page implements Serializable{

    private Integer action_id;//权限id
    private String action_name;//权限名称
    private String action_flag;//权限标记
    private String is_delete;//是否删除，y为已删除，n为未删除

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public String getAction_flag() {
        return action_flag;
    }

    public void setAction_flag(String action_flag) {
        this.action_flag = action_flag;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}