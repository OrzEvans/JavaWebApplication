package com.web.application.project.annotation;

import java.lang.annotation.*;

/**
 * 查询参数注解,用于指明该参数需要做查询,该参数必须是实体类
 * @author Evans
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented  
@Inherited
public @interface SearchParam {

}
