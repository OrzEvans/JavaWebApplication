package com.web.application.project.annotation;

import java.lang.annotation.*;

/**
 * 指定需要进行模糊查询参数处理的方法
 * @author Evans
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented  
@Inherited
public @interface Search {

}
