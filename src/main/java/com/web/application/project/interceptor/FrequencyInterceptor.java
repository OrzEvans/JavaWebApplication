/**
 * 文件名称:FrequencyInterceptor.java 创建者:SEO1 创建日期:2017年1月11日
 */
package com.web.application.project.interceptor;

import com.web.application.project.entity.FrequencyStruct;
import com.web.application.tools.DevelopTools;
import com.web.application.tools.OptionEnum;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * FrequencyInterceptor
 * <hr/>
 * <li>使用时注意第89行获取IP的方法</li>
 * @Title  访问频率限制
 * @author Evans
 * @date 2017年1月11日
 * @version 1.0
 */
public class FrequencyInterceptor implements HandlerInterceptor{
		
	 	private static final int MAX_BASE_STATION_SIZE = 100000;  
	    private static Map<String, FrequencyStruct> BASE_STATION = new HashMap<String, FrequencyStruct>(MAX_BASE_STATION_SIZE);  
	    private static final float SCALE = 0.75F;  
	    private static final int MAX_CLEANUP_COUNT = 500;  
	    private static final int CLEANUP_INTERVAL = 1000;  
	    private Object syncRoot = new Object();  
	    private int cleanupCount = 0;
	    private static final String FREQUENCY_NAME="IP";

		private Logger logger=Logger.getLogger(FrequencyInterceptor.class);

	    @Resource
	    private OptionEnum optionEnum;

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			boolean going = true;
		    going=handleFrequency(request,response);
		    if(!going){
		    	response.sendRedirect(request.getContextPath()+optionEnum.getFREQUENCY_FORBID_REDIRECT());
			}
			return going;
		}

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {

			
		}
	
		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
				Exception ex) throws Exception {

			
		}
		/**
		 * 主处理方法
		 * @param request
		 * @param response
		 * @return
		 * @author Evans
		 */
	    private boolean handleFrequency(HttpServletRequest request, HttpServletResponse response) {

			boolean going = true;

			String name = FREQUENCY_NAME;
			int limit = optionEnum.getFERQUENCY_LIMIT();
			int time = optionEnum.getFREQUENCY_TIME();
			String session = request.getSession().getId();
			if(time == 0 || limit == 0) {
				going = false;
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return going;
			}

			long currentTimeMilles = System.currentTimeMillis() / 1000;
			//Nginx获取ip
			String ip = DevelopTools.getIpAddress(request);
			//普通获取ip
//        String ip = getRemoteIp(request);
			String key = name + "_" + ip;
			FrequencyStruct frequencyStruct = BASE_STATION.get(key);
			//总次数是否超过上限
			if(frequencyStruct!=null&&frequencyStruct.getIPforbidCount()>=optionEnum.getFREQUENCY_FORBID_IP_COUNT()){
				if(currentTimeMilles-frequencyStruct.getIPforbidTime()>optionEnum.getFREQUENCY_FORBID_IP_TIME()){
					frequencyStruct.resetIpForbid();
					frequencyStruct.setMessage(null);
				}else if(frequencyStruct.getMessage()==null){
					logForbidLogger(ip,frequencyStruct,"ip");
					return false;
				}else{
					return false;
				}
			} else if(frequencyStruct!=null&&frequencyStruct.getForbidCount()>=optionEnum.getFREQUENCY_FORBID_COUNT()){
				if(currentTimeMilles-frequencyStruct.getForbidTime()>optionEnum.getFREQUENCY_FORBID_TIME()){
					frequencyStruct.resetForbid();
					frequencyStruct.reset(currentTimeMilles);
					frequencyStruct.resetSession(currentTimeMilles,session);
					frequencyStruct.setMessage(null);
				}else if(frequencyStruct.getMessage()==null){
					logForbidLogger(ip,frequencyStruct,"session");
					return false;
				}else{
					return false;
				}
			}else if(frequencyStruct!=null&&frequencyStruct.getForbidVisitCount()>=optionEnum.getFREQUENCY_FORBID_VISIT_COUNT()){
				if(currentTimeMilles-frequencyStruct.getForbidVisitEnd()>optionEnum.getFREQUENCY_FORBID_TIME()){
					frequencyStruct.resetVisit(currentTimeMilles);
					frequencyStruct.setMessage(null);
				}else if(frequencyStruct.getMessage()==null){
					logForbidLogger(ip,frequencyStruct,"visit");
					return false;
				}else{
					return false;
				}
			}

			if(frequencyStruct == null) {
				frequencyStruct = new FrequencyStruct();
				frequencyStruct.setUniqueKey(key);
				frequencyStruct.setStart(currentTimeMilles);
				frequencyStruct.setEnd(currentTimeMilles);
				frequencyStruct.setSessionStart(currentTimeMilles);
				frequencyStruct.setSessionEnd(currentTimeMilles);
				frequencyStruct.setLimit(limit);
				frequencyStruct.setTime(time);
				frequencyStruct.getAccessPoints().add(currentTimeMilles);
				frequencyStruct.getSessionCounts().add(session);
				synchronized (syncRoot) {
					BASE_STATION.put(key, frequencyStruct);
				}
				if(BASE_STATION.size() > MAX_BASE_STATION_SIZE * SCALE) {
					cleanup(currentTimeMilles);
				}
			} else {
				frequencyStruct.setEnd(currentTimeMilles);
				frequencyStruct.setSessionEnd(currentTimeMilles);
				frequencyStruct.getAccessPoints().add(currentTimeMilles);
				List<String> list = frequencyStruct.getSessionCounts();
				if(!list.contains(session)){
					frequencyStruct.getSessionCounts().add(session);
				}
			}
			//判断referer和location
			String referer=request.getHeader("Referer");
			String location=request.getRequestURL().toString();
			if(referer==null&&location.indexOf(optionEnum.getFREQUENCY_FORBID_URL())>-1){
				frequencyStruct.setForbidVisitCount();
				frequencyStruct.setForbidVisitStart(currentTimeMilles);
			}

			//时间是否有效
			if(frequencyStruct.getEnd() - frequencyStruct.getStart() >= time) {
				frequencyStruct.reset(currentTimeMilles);
			} else {
				int count = frequencyStruct.getAccessPoints().size();
				if(count > limit) {
					going = false;
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					frequencyStruct.setIPforbidCount();
				}
			}
			if(frequencyStruct.getIPforbidCount()>=optionEnum.getFREQUENCY_FORBID_IP_COUNT()){
				going = false;
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				frequencyStruct.setIPforbidTime(currentTimeMilles);
			}


			//session时间是否超时，超时则重置
			if(frequencyStruct.getSessionEnd()-frequencyStruct.getSessionStart()>=optionEnum.getFREQUENCY_SESSION_TIME()){
				frequencyStruct.resetSession(currentTimeMilles, session);
			}
			//session总数超过上限，则禁止访问
			int count = frequencyStruct.getSessionCounts().size();
			if(count>optionEnum.getFREQUENCY_SESSION()){
				going = false;
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				frequencyStruct.addForbidCount();
				frequencyStruct.setForbidTime(currentTimeMilles);
			}
			//限制路径访问是否超时，超时则重置
			if(frequencyStruct.getForbidVisitEnd()-frequencyStruct.getForbidVisitStart()>=optionEnum.getFREQUENCY_FORBID_VISIT_TIME()){
				frequencyStruct.resetVisit(currentTimeMilles);
			}
			//路径访问限制超过上限，则禁止访问
			if(frequencyStruct.getForbidVisitCount()>=optionEnum.getFREQUENCY_FORBID_VISIT_COUNT()){
				going = false;
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				frequencyStruct.setForbidVisitEnd(currentTimeMilles);
			}

			return going;
	    }  
	  
	    private void cleanup(long currentTimeMilles) {  
	        synchronized (syncRoot) {  
	            Iterator<String> it = BASE_STATION.keySet().iterator();  
	            while(it.hasNext()) {  
	                String key = it.next();  
					FrequencyStruct struct = BASE_STATION.get(key);  
	                if((currentTimeMilles - struct.getEnd()) > struct.getTime()) {  
	                    it.remove();  
	                }  
	            }  
	              
	            if((MAX_BASE_STATION_SIZE - BASE_STATION.size()) > CLEANUP_INTERVAL) {  
	                cleanupCount = 0;  
	            } else {  
	                cleanupCount++;  
	            }  
	              
	            if(cleanupCount > MAX_CLEANUP_COUNT ) {  
	                randomCleanup(MAX_CLEANUP_COUNT);  
	            }  
	        }  
	    }  
	  
	    /** 
	     * 随机淘汰count个key 
	     *  
	     * @param count
	     */  
	    private void randomCleanup(int count) {  
	        //防止调用错误  
	        if(BASE_STATION.size() < MAX_BASE_STATION_SIZE * SCALE) {  
	            return;  
	        }  
	          
	        Iterator<String> it = BASE_STATION.keySet().iterator();  
	        Random random = new Random();  
	        int tempCount = 0;  
	          
	        while(it.hasNext()) {  
	            if(random.nextBoolean()) {  
	                it.remove();  
	                tempCount++;  
	                if(tempCount >= count) {  
	                    break;  
	                }  
	            }  
	        }  
	    }  
	    /**
	     * 获取ip
	     * @param request
	     * @return
	     * @author Evans
	     */
		private String getRemoteIp(HttpServletRequest request) {  
	        String ip = request.getHeader("x-forwarded-for");  
	        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	  
	        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	  
	        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	  
	        return ip;  
	    }

		/**
		 * 纪录禁止访问日志
		 * @param ip 当前IP
		 * @param frequencyStruct
		 * @param type
		 */
		private void logForbidLogger(String ip,FrequencyStruct frequencyStruct,String type){
			StringBuffer sb=new StringBuffer();
			if("session".equalsIgnoreCase(type)){
				sb.append("超过Session限制总数：").append("当前禁止对象为[").append(frequencyStruct.getUniqueKey()).append("],当前限制总数为[").append(optionEnum.getFREQUENCY_FORBID_COUNT()).append("],禁止ip为[")
						.append(ip).append("],禁止开始时间为[").append(DevelopTools.getFormatedDateString(8)).append("],当前禁止时长为[")
						.append(optionEnum.getFREQUENCY_FORBID_TIME()).append("秒]");
			}else if("visit".equalsIgnoreCase(type)){
				sb.append("超过路径限制总数：").append("当前禁止对象为[").append(frequencyStruct.getUniqueKey()).append("],当前限制总数为[").append(optionEnum.getFREQUENCY_FORBID_VISIT_COUNT()).append("],禁止ip为[")
						.append(ip).append("],禁止开始时间为[").append(DevelopTools.getFormatedDateString(8)).append("],当前禁止时长为[")
						.append(optionEnum.getFREQUENCY_FORBID_VISIT_TIME()).append("秒]");
			}else if("ip".equalsIgnoreCase(type)){
				sb.append("超过IP限制总数：").append("当前禁止对象为[").append(frequencyStruct.getUniqueKey()).append("],当前限制总数为[").append(optionEnum.getFREQUENCY_FORBID_IP_COUNT()).append("],禁止ip为[")
						.append(ip).append("],禁止开始时间为[").append(DevelopTools.getFormatedDateString(8)).append("],当前禁止时长为[")
						.append(optionEnum.getFREQUENCY_FORBID_IP_TIME()).append("秒]");
			}else{
				return;
			}
			frequencyStruct.setMessage(sb.toString());
			logger.error(frequencyStruct.getMessage());
		}
}
