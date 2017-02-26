/**
 * 文件名称:DefaultLogMethod.java 创建者:evans 创建日期:2017年1月13日
 */
package com.web.application.project.aop;

import org.aspectj.lang.JoinPoint;

/**
 * DefaultLogMethod
 * @Title  默认日志纪录实现类 TODO需完成具体纪录内容(数据库纪录/文件纪录/log4j纪录)
 * @author evans
 * @date 2017年1月13日
 * @version 1.0
 */
public class DefaultLogMethod implements LogMethod {

	@Override
	public void logAction(JoinPoint jp) {
		
	}
}
