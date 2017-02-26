/**
 * 文件名称:LogEntity.java 创建者:Evans 创建日期:2017年1月12日
 */
package com.web.application.project.entity;

import java.io.Serializable;

import com.web.application.project.aop.LogContext;

/**
 * LogEntity
 * @Title  记录日志实体类
 * @author Evans
 * @date 2017年1月12日
 * @version 1.0
 */
public class LogEntity implements Serializable,LogContext {

	private static final long serialVersionUID = -2333920677001311886L;
	
	private Integer log_id;//日志id
	private Integer operator_id;//操作者id
	private String  operator_type;//操作者类型
	private String  operation_model;//操作模块
	private String  operation_content;//操作内容
	private String  operation_time;//操作时间
	private String  is_delete;//是否删除，y为已删除，n为未删除
	
	public Integer getLog_id() {
		return log_id;
	}
	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}
	public Integer getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(Integer operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
	}
	public String getOperation_model() {
		return operation_model;
	}
	public void setOperation_model(String operation_model) {
		this.operation_model = operation_model;
	}
	public String getOperation_content() {
		return operation_content;
	}
	public void setOperation_content(String operation_content) {
		this.operation_content = operation_content;
	}
	public String getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	@Override
	public Integer getOperatorId() {
		return this.operator_id;
	}
	
	@Override
	public String getOperationModel() {
		return this.operation_model;
	}
	
	@Override
	public String getOperationContent() {
		return this.operation_content;
	}
	
}
/*
日志建表语句
DROP TABLE IF EXISTS `log_entity` ;
CREATE TABLE IF NOT EXISTS `log_entity` (
  `log_id` INT NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `operator_id` INT NOT NULL COMMENT '操作者id',
  `operator_type` VARCHAR(20) NOT NULL   COMMENT '操作者类型',
  `operation_model` VARCHAR(20) NOT NULL   COMMENT '操作模块',
  `operation_content` VARCHAR(255)  NOT NULL  COMMENT '操作内容',
  `operation_time` TIMESTAMP NOT NULL  COMMENT '操作时间',
  `is_delete` VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y为已删除，N为未删除',
  PRIMARY KEY (`log_id`))
ENGINE = InnoDB
COMMENT = '日志记录表';
*/