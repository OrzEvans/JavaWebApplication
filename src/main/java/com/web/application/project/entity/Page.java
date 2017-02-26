package com.web.application.project.entity;

import net.sf.json.JSONObject;

public class Page {
	// 每页最大行数，是常量
	private int pageSize = 20;
	// 当前客户选择的页码，是页面传入的条件，默认为1
	private int currentPage = 1;
	// 分页查询的起始数
	private Integer begin = null;
	// session令牌，防止表单重复提交
	private String tokenKey;

	/**
	 * 获取分页查询起始数，若手动设置了begin则返回设置的begin，否则返回根据currentPage和pageSize自动计算begin
	 * 
	 * @return limit的第一个参数
	 */
	public int getBegin() {
		if (begin == null) {
			return (currentPage - 1) * pageSize;
		}
		return begin;
	}

	/**
	 * 设置分页查询起始数，默认为null不设置则会根据currentPage和pageSize自动计算begin
	 * 
	 * @param begin
	 *            LIMIT查询的一个参数，若设置这个值则会以此数返回begin
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}

	/**
	 * 获取当前页码数
	 * 
	 * @return 当前页码数
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置分页查询的查询条目数,默认为15
	 * 
	 * @param pageSize
	 *            分页查询的查询条目数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取分页查询的查询条目数,默认为15
	 * 
	 * @return 分页查询的查询条目数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置当前页码数，默认为1
	 * 
	 * @param currentPage
	 *            当前页码数
	 */
	public void setCurrentPage(String currentPage) {
		int page=1;
		try{
			currentPage=currentPage==null?"1":currentPage;
			page=Integer.valueOf(currentPage);
		}catch (Exception e){
			e.printStackTrace();
		}
		this.currentPage = page;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
