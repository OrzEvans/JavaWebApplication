package com.web.application.project.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.web.application.project.annotation.Log;

/**
 * 日志切面
 * @author Evans
 * @version 1.0
 * @date 2017年1月11日
 */
@Aspect
@Component
public class LogAop {
	/**
	 * 环绕通知执行session校验
	 * @param jp
	 * @param logAnnotation log注解
	 * @author Evans
	 * @date 2017年1月11日
	 */
	@AfterReturning("@annotation(com.web.application.project.annotation.Log)&& @annotation(logAnnotation)")
	public void afterExec(JoinPoint jp,Log logAnnotation) {
		String name=logAnnotation.name();
		LogMethod logMethod=null;
		try {
			logMethod=AopFactory.getLogAopInstance(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(logMethod!=null){
			logMethod.logAction(jp);
		}
	}
}
