package com.web.application.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 日志记录注解
 * <p>该注解若不指定名称，则默认使用DefaultLogMethod方法进行日志纪录</p>
 * @author Evans
 * @date 2017年1月13日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented  
@Inherited
public @interface Log {
	/**
	 * 日志注解的名字，用于指定不同的日志纪录方法
	 */
	String name() default "default";
}
