/**
 * 文件名称:LogMethod.java 创建者:evans 创建日期:2017年1月13日
 */
package com.web.application.project.aop;

import org.aspectj.lang.JoinPoint;

import com.web.application.project.entity.LogEntity;
import com.web.application.tools.DevelopTools;

/**
 * LogMethod
 * <p>实现该接口时请定义名称为Log注解中的name+LogMethod</p>
 * @Title  日志纪录接口，实现该接口以实现不同的日志纪录方法
 * @author evans
 * @date 2017年1月13日
 * @version 1.0
 */
public interface LogMethod {
	/**
	 * logAction
	 * @Title  纪录日志
	 * @param jp 切点，用于获取方法的详细信息
	 * @author evans
	 * @date 2017年1月13日
	 */
	public void logAction(JoinPoint jp);
	/**
	 * setLogEntity
	 * @Title  设置日志的默认方法，实现类可重写该方法
	 * @param logContext 日志的上下文，日志实体类需要实现该接口
	 * @return LogEntity 日志实体类
	 * @author evans
	 * @date 2017年1月13日
	 */
	public default <T> LogEntity setLogEntity(LogContext logContext){
		LogEntity logEntity = new LogEntity();
		logEntity.setOperator_id(logContext.getOperatorId());
		logEntity.setOperation_model(logContext.getOperationModel());
		logEntity.setOperation_content(logContext.getOperationContent());
		logEntity.setOperation_time(DevelopTools.getFormatedDateString(8));
		logEntity.setOperator_type("默认类型");
		return logEntity;
	}
}
