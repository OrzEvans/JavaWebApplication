package com.web.application.tools;

import net.sf.json.JSONObject;

import java.io.Serializable;
/**
 * <b>用于封装服务器返回数据的结果</b>
 * <li>status:状态，使用1表示成功，使用0表示失败</li>
 * <li>data:数据，用于存储向前台发送的数据</li>
 * <li>msg:信息，用于存储向前台发送的信息</li>
 * @author Evans
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -3923689106779103727L;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 信息
	 */
	private String msg;
	/**
	 * 数据
	 */
	private T data;
	/**
	 * 用于分页的总数
	 */
	private Integer count;
	/**
	 * 用于分页的数据分页数量
	 */
	private Integer pageSize;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
	
}
