/**
 * 文件名称:LogContext.java 创建者:Evans 创建日期:2017年1月16日
 */
package com.web.application.project.aop;

/**
 * LogContext
 * @Title  日志记录的上下文
 * @author Evans
 * @date 2017年1月16日
 * @version 1.0
 */
public interface LogContext {
	/**
	 * getOperatorId
	 * @Title  获取操作者ID
	 * @return Integer
	 * @author Evans
	 * @date 2017年1月16日
	 */
	public Integer getOperatorId();
	/**
	 * getOperationModel
	 * @Title  获取操作模块
	 * @return String
	 * @author Evans
	 * @date 2017年1月16日
	 */
	public String  getOperationModel();
	/**
	 * getOperationContent
	 * @Title  获取操作内容
	 * @return String
	 * @author Evans
	 * @date 2017年1月16日
	 */
	public String  getOperationContent();
	
}
