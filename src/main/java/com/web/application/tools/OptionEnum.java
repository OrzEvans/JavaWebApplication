package com.web.application.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OptionEnum
 * @Title  静态数据类
 * @author Evans
 * @date 2017年1月11日
 * @version 1.0
 * <hr/>
 * <li>采用依赖注入的方式使用</li>
 * <li>修改时修改optionEnum.properties文件即可</li>
 * <li>加入新常量时增加private变量(记得写文档注释)，并使用大写，然后增加setter和getter方法</li>
 */
@Component
public class OptionEnum {
	/**
	 * 限制频率次数
	 */
	@Value("${frequency.limit}")
	private Integer FERQUENCY_LIMIT;
	/**
	 * 限制频率时间(秒)
	 */
	@Value("${frequency.time}")
	private Integer FREQUENCY_TIME;
	/**
	 * 限制session个数
	 */
	@Value("${frequency.session}")
	private Integer FREQUENCY_SESSION;
	/**
	 * 限制session时间(秒)
	 */
	@Value("${frequency.session.time}")
	private Integer FREQUENCY_SESSION_TIME;
	/**
	 * 禁止访问次数
	 */
	@Value("${frequency.forbid.count}")
	private Integer FREQUENCY_FORBID_COUNT;
	/**
	 * 禁止访问时间(秒)
	 */
	@Value("${frequency.forbid.time}")
	private Integer FREQUENCY_FORBID_TIME;

	/**
	 * 通过路径判断时的禁止访问次数
	 */
	@Value("${frequency.forbid.visit.count}")
	private int FREQUENCY_FORBID_VISIT_COUNT;
	/**
	 * 通过路径判断时的禁止访问时间
	 */
	@Value("${frequency.forbid.visit.time}")
	private Integer FREQUENCY_FORBID_VISIT_TIME;
	/**
	 * IP禁止访问次数
	 */
	@Value("${frequency.forbid.ip.count}")
	private Integer FREQUENCY_FORBID_IP_COUNT;
	/**
	 * IP禁止访问时间
	 */
	@Value("${frequency.forbid.ip.time}")
	private Integer FREQUENCY_FORBID_IP_TIME;
	/**
	 * 禁止访问的跳转页面
	 */
	@Value("${frequency.forbid.redirect}")
	private String FREQUENCY_FORBID_REDIRECT;
	/**
	 * IP禁止访问时间
	 */
	@Value("${frequency.forbid.url}")
	private String FREQUENCY_FORBID_URL;
	
	
	
	//============================以下为Setter和Getter方法==========================================================
	public Integer getFERQUENCY_LIMIT() {
		return FERQUENCY_LIMIT;
	}
	public void setFERQUENCY_LIMIT(Integer fERQUENCY_LIMIT) {
		FERQUENCY_LIMIT = fERQUENCY_LIMIT;
	}
	public Integer getFREQUENCY_TIME() {
		return FREQUENCY_TIME;
	}
	public void setFREQUENCY_TIME(Integer fREQUENCY_TIME) {
		FREQUENCY_TIME = fREQUENCY_TIME;
	}
	public Integer getFREQUENCY_SESSION() {
		return FREQUENCY_SESSION;
	}
	public void setFREQUENCY_SESSION(Integer fREQUENCY_SESSION) {
		FREQUENCY_SESSION = fREQUENCY_SESSION;
	}
	public Integer getFREQUENCY_SESSION_TIME() {
		return FREQUENCY_SESSION_TIME;
	}
	public void setFREQUENCY_SESSION_TIME(Integer fREQUENCY_SESSION_TIME) {
		FREQUENCY_SESSION_TIME = fREQUENCY_SESSION_TIME;
	}
	public Integer getFREQUENCY_FORBID_COUNT() {
		return FREQUENCY_FORBID_COUNT;
	}
	public void setFREQUENCY_FORBID_COUNT(Integer fREQUENCY_FORBID_COUNT) {
		FREQUENCY_FORBID_COUNT = fREQUENCY_FORBID_COUNT;
	}
	public Integer getFREQUENCY_FORBID_TIME() {
		return FREQUENCY_FORBID_TIME;
	}
	public void setFREQUENCY_FORBID_TIME(Integer fREQUENCY_FORBID_TIME) {
		FREQUENCY_FORBID_TIME = fREQUENCY_FORBID_TIME;
	}

	public int getFREQUENCY_FORBID_VISIT_COUNT() {
		return FREQUENCY_FORBID_VISIT_COUNT;
	}

	public void setFREQUENCY_FORBID_VISIT_COUNT(int FREQUENCY_FORBID_VISIT_COUNT) {
		this.FREQUENCY_FORBID_VISIT_COUNT = FREQUENCY_FORBID_VISIT_COUNT;
	}

	public Integer getFREQUENCY_FORBID_VISIT_TIME() {
		return FREQUENCY_FORBID_VISIT_TIME;
	}

	public void setFREQUENCY_FORBID_VISIT_TIME(Integer FREQUENCY_FORBID_VISIT_TIME) {
		this.FREQUENCY_FORBID_VISIT_TIME = FREQUENCY_FORBID_VISIT_TIME;
	}

	public Integer getFREQUENCY_FORBID_IP_COUNT() {
		return FREQUENCY_FORBID_IP_COUNT;
	}

	public void setFREQUENCY_FORBID_IP_COUNT(Integer FREQUENCY_FORBID_IP_COUNT) {
		this.FREQUENCY_FORBID_IP_COUNT = FREQUENCY_FORBID_IP_COUNT;
	}

	public Integer getFREQUENCY_FORBID_IP_TIME() {
		return FREQUENCY_FORBID_IP_TIME;
	}

	public void setFREQUENCY_FORBID_IP_TIME(Integer FREQUENCY_FORBID_IP_TIME) {
		this.FREQUENCY_FORBID_IP_TIME = FREQUENCY_FORBID_IP_TIME;
	}

	public String getFREQUENCY_FORBID_REDIRECT() {
		return FREQUENCY_FORBID_REDIRECT;
	}

	public void setFREQUENCY_FORBID_REDIRECT(String FREQUENCY_FORBID_REDIRECT) {
		this.FREQUENCY_FORBID_REDIRECT = FREQUENCY_FORBID_REDIRECT;
	}

	public String getFREQUENCY_FORBID_URL() {
		return FREQUENCY_FORBID_URL;
	}

	public void setFREQUENCY_FORBID_URL(String FREQUENCY_FORBID_URL) {
		this.FREQUENCY_FORBID_URL = FREQUENCY_FORBID_URL;
	}
}
