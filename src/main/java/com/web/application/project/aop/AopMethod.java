/**
 * 文件名称:AopSpecialMethod.java 创建者:Evans 创建日期:2017年1月11日
 */
package com.web.application.project.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AopMethod
 * <p>实现该接口时请定义名称为session注解中的name+AopMethod</p>
 * @Title  此接口用于定义针对特殊session注解name的不同处理方法
 * @author Evans
 * @date 2017年1月11日
 * @version 1.0
 */
public interface AopMethod {
	/**
	 * action
	 * @Title 针对不同name的Session注解进行不同的处理
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return boolean 返回boolean值，验证通过返回true，验证失败返回false，当返回false时切面方法则直接返回不再进行session验证
	 * @throws Exception
	 * @author Evans
	 * @date 2017年1月11日
	 */
	public boolean action(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
