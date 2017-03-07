package com.web.application.project.annotation;

import java.lang.annotation.*;

/**
 * 查询注解
 * <p>该注解需标记在需要查询的属性上</p>
 * @author Evans
 * @date 2017年3月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented  
@Inherited
public @interface SearchField {
}
