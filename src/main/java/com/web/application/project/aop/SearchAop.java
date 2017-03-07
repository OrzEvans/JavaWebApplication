package com.web.application.project.aop;

import com.web.application.project.annotation.SearchParam;
import com.web.application.tools.DevTools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 模糊查询参数处理
 * @author Evans
 * @version 1.0
 * @date 2017年3月4日
 */
@Aspect
@Component
public class SearchAop {

    /**
     * 在查询方法执行前,将需要模糊查询的参数,增加%处理
     * @param jp
     */
	@Before("@annotation(com.web.application.project.annotation.Search)")
	public void beforeExec(JoinPoint jp) {
		Object[] objects=jp.getArgs();
		Object target=jp.getTarget();
		String name=jp.getSignature().getName();
		Method[] methods=target.getClass().getDeclaredMethods();
		Method currentMethod=null;
		for(Method method:methods){
			if(name.equals(method.getName())){
				currentMethod=method;
				break;
			}
		}
		Annotation[][] annotations=currentMethod.getParameterAnnotations();
		for(int i=0;i<objects.length;i++){
            if(annotations[i].length>0&&objects[i]!=null){
                Class<?> objClass=objects[i].getClass();
                for(int j= 0;j<annotations[i].length;j++){
                    Class<?> annotationClass= annotations[i][j].annotationType();
                    if(SearchParam.class==annotationClass){
                        if(String.class==objClass){
                            DevTools.addLikeParameter(objects[i].toString());
                        }else{
                            DevTools.addLikeParameterWithObject(objects[i]);
                        }
                    }
                }
            }
        }
	}

    /**
     * 在查询方法执行完毕后,将查询参数去除%处理
     * @param jp
     */
	@After("@annotation(com.web.application.project.annotation.Search)")
	public void afterExec(JoinPoint jp) {
        Object[] objects=jp.getArgs();
        Object target=jp.getTarget();
        String name=jp.getSignature().getName();
        Method[] methods=target.getClass().getDeclaredMethods();
        Method currentMethod=null;
        for(Method method:methods){
            if(name.equals(method.getName())){
                currentMethod=method;
                break;
            }
        }
        Annotation[][] annotations=currentMethod.getParameterAnnotations();
        for(int i=0;i<objects.length;i++){
            if(annotations[i].length>0&&objects[i]!=null){
                Class<?> objClass=objects[i].getClass();
                for(int j= 0;j<annotations[i].length;j++){
                    Class<?> annotationClass= annotations[i][j].annotationType();
                    if(SearchParam.class==annotationClass){
                        if(String.class==objClass){
                            DevTools.removeLikeParameter(objects[i].toString());
                        }else{
                            DevTools.removeLikeParameterWithObject(objects[i]);
                        }
                    }
                }
            }
        }
	}
}
