/**
 * 文件名称:AopFactory.java 创建者:Evans 创建日期:2017年1月11日
 */
package com.web.application.project.aop;

/**
 * AopFactory
 * @Title  用于获取不同aop处理接口的实现类
 * @author Evans
 * @date 2017年1月11日
 * @version 1.0
 */
public class AopFactory {
	/**
	 * getLogAopInstance
	 * @Title  获取LogMethod接口的实现类
	 * @param name Log注解中的指定name
	 * @return LogMethod对象
	 * @throws Exception ClassNotFoundException,InstantiationException, IllegalAccessException
	 * @author evans
	 * @date 2017年1月13日
	 */
	public static LogMethod getLogAopInstance(String name) throws Exception{
		StringBuffer sb = new StringBuffer();
		String packageName=LogMethod.class.getPackage().getName();
		sb.append(packageName).append(".").append(name.substring(0,1).toUpperCase()).append(name.substring(1)).append("LogMethod");
		LogMethod logMethod=null;
		logMethod= (LogMethod)Class.forName(sb.toString()).newInstance();
		return logMethod;
	}
	
}
