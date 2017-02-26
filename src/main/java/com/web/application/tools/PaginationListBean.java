package com.web.application.tools;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

/*
 * @(#)PaginationBean.java
 * Copyright (c) by RenMai Corporation. All Rights Reserved.
 * 
 * @version 1.0 2003-2-1
 * @author wang.li
 */

public class PaginationListBean extends BodyTagSupport  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int list_number;
	private int page_size;
	private boolean hidden_anchor = false;
	private boolean display_body = false;
	private String anchor_name = null;
	private String post_url = null;
	private String form_name = null;
	private String encoding = null;
	private String css_name = null;
	private int frame_size;
	public void setList_number(String newValue) {
		try {
			list_number = Integer.parseInt(newValue);	
		}catch(Exception e) { }
	}
	
	public void setPost_url(String newValue) {
		post_url = newValue.trim();
	}

	public void setPage_size(String newValue) {
		try {
			page_size = Integer.parseInt(newValue);	
		}catch(Exception e) { }
	}
	
	public void setPage_size(int newValue) {
		page_size = newValue;
	}
	
	public void setCss_name(String newValue) {
		css_name = newValue.trim();	
	}
	
	public void setHidden_anchor(String newValue) {
		try {
			hidden_anchor = new Boolean(newValue).booleanValue();
		}catch(Exception e) { }
	}

	public void setDisplay_body(String newValue) {
		try {
			display_body = new Boolean(newValue).booleanValue();
		}catch(Exception e) { }
	}

	public void setAnchor_name(String newValue) {
		anchor_name = newValue.trim();
	}
	
	public void setForm_name(String newValue) {
		form_name = newValue.trim();
	}

	public void setEncoding(String newValue) {
		encoding = newValue.trim();
	}
	
	public int doStartTag() {
		if(display_body) {
			return EVAL_BODY_BUFFERED;
		}else {
			return SKIP_BODY;
		}
	}

	public int doEndTag() {
		if(list_number>0){//有记录的情况，显示分页
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			String queryString = request.getQueryString();
			if(css_name==null){
				css_name="text-center_pagination";
			}
			@SuppressWarnings("unused")
			String name = null, url = null;
			if(post_url == null) {
				url = request.getRequestURI();
			}else {
				url = post_url;
			}		
			if(anchor_name != null) url += "#" + anchor_name;
	
			StringBuffer sb = new StringBuffer();
			sb.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"fy-table\"><tr> <td> <form id=\"page\"  method=\"post\"  name=\"");
			if(form_name == null) {
				sb.append("page");
			}else {
				sb.append(form_name);
			}
			sb.append("\">");
			//\n\t<input type=\"hidden\"  name=\"currentPage\" id=\"currentPage\" value=\"");
	//		if(request.getParameter("currentPage") != null) sb.append(request.getParameter("currentPage"));
	//		if(request.getParameter("currentPage") == null) sb.append(1);
	//
	//		sb.append("\"/>");
				Enumeration<String> names = request.getParameterNames();
				while(names.hasMoreElements()) {
					name = (String)names.nextElement();
					if(name.equals("currentPage")) continue;
					//get方式带参数不添加 input 分页逗号问题
					if(queryString!=null && queryString.contains(name)) continue;
					String[] tmp_value = request.getParameterValues(name);
	//				List<String> list = new ArrayList<String>();
					for(int k = 0; k < tmp_value.length; k++) {
						if(tmp_value[k].indexOf("<") > -1 || tmp_value[k].indexOf(">") > -1)continue;
	//					if(list.contains(tmp_value[k]))continue;
	//					list.add(tmp_value[k]);
						sb.append("\n\t<input type=\"hidden\" name=\"");
						sb.append(name).append("\" value=\"");
						if(encoding != null && request.getMethod().equalsIgnoreCase("GET")) {
							try {
								tmp_value[k] = new String(tmp_value[k].getBytes("ISO8859-1"), encoding);
							}catch(UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						sb.append(tmp_value[k]).append("\"/>");
					}
				}
			if(!hidden_anchor) {
				String tmp = request.getParameter("currentPage");
				int page_no = 0;
				int page_count = getPage_count();
				if(tmp != null) {
					try {
						page_no = Integer.parseInt(tmp);
					}catch(Exception e) { }
				}
				if(page_no > page_count) page_no = page_count;
				if(page_no < 1) page_no = 1;
				sb.append("\n\t<div id=\"pageDiv\" class=\"fy-con ").append(css_name.split(",")[0]).append("\" ><ul class=\"").append(css_name.split(",")[1]).append("\">");
				int frame_no = getFrameNo(page_no);
				int frame_count = getFrameCount();
				if(frame_no == 1) {	// previous page
					sb.append("<li></li>");
				}else {
					sb.append("<li onclick ='turnpage(").append(getPrevFramePageNo(page_no)).append(")'></li>");
				}
				sb.append(this.getPageList(page_no));
				if(frame_no == frame_count) {	// next page
					sb.append("<li class='last'></li>");
				}else {
					sb.append("<li class='last' onclick ='turnpage(").append(getNextFramePageNo(page_no)).append(")'></li>");
				}
				sb.append("<span class='span-f' >共<label>"+page_count+"</label> 页</span>");
				sb.append("<span class='span-shuru'>到第");
				sb.append("<input type=\"text\" onkeyup=\"numCheck()\" name=\"currentPage\" id=\"currentPage\" value=\"");
				if(request.getParameter("currentPage") != null) sb.append(request.getParameter("currentPage"));
				if(request.getParameter("currentPage") == null) sb.append(1);
				
				sb.append("\"/> 页</span><a class='fy-btn' onclick='pageSubmit()'>GO</a>");
				sb.append("</ul></div>");
				
				// javascript validate function
				sb.append("\n<script language=\"JavaScript\">");
				sb.append("\n\tfunction turnpage(theNo) {");
				sb.append("\n\t\tif(theNo < 1) theNo = 1;");
				sb.append("\n\t\t$(\"#currentPage\").val(theNo);");
				//sb.append("\n\t\tpage.currentPage.value = theNo < ").append(page_count).append("? theNo:").append(page_count).append(";");
				//sb.append("\n\t\tpage.action = \"").append(url).append("\";");
				//sb.append("\n\t\tpage.target = \"_self\";");
				sb.append("\n\t\t$(\"#page\").submit();}");
				sb.append("\n\tfunction pageSubmit() {");
				sb.append("\n\t var toPage = $('#currentPage').val();");
				sb.append("\n\t if(toPage > ").append(page_count).append("|| toPage <= 0) $('#currentPage').val(1);");
				sb.append("\n\t\t$(\"#page\").submit();");
				sb.append("\n\t}\n");
				sb.append("\n\tfunction numCheck() {");
				sb.append("\n\t var toPage = $('#currentPage').val();");
				sb.append("\n\t if(isNaN(toPage)) $('#currentPage').val(1);");
				sb.append("\n\t}\n</script>");
			}
			
			if(bodyContent != null) {
				sb.append(bodyContent.getString());	// body content;
			}
			sb.append("\n</form> </td></tr></table>");	// end form
//			System.out.println(sb.toString());
			try {
				pageContext.getOut().write(sb.toString());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("<p class=\"no-msg\" id='no_content'></p>");
			try {
				pageContext.getOut().write(sb.toString());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}


	private int getPage_count() {
		if(page_size == 0) return 0;
		else {
			return (list_number - 1) / page_size + 1;
		}
	}
	private int getFrameCount() {
		int page_count = getPage_count();
		return (page_count - 1) / frame_size + 1;
	}
	private String getPageList(int page_no){
		StringBuffer sb = new StringBuffer();
		int frame_no = getFrameNo(page_no);
		int start = (frame_no - 1) * frame_size +1;
		int end = (frame_no) * frame_size;
		int page_count = getPage_count();
		if(end > page_count)end = page_count;
		for(int i=start;i<=end;i++){
			if(i == page_no){
				sb.append("").append("<li class='fen-ye' onclick='turnpage(").append(i).append(");'>").append(i).append("</li>");
			}else{
				sb.append("").append("<li onclick='turnpage(").append(i).append(");'>").append(i).append("</li>");
			}
			
		}
		sb.append("");
	
		return sb.toString();
	}
	private int getPrevFramePageNo(int page_no){
		return (getFrameNo(page_no) - 1) * frame_size ;
	}
	private int getNextFramePageNo(int page_no){
		return (getFrameNo(page_no)) * frame_size + 1;
	}
	private int getFrameNo(int page_no){
		return (page_no - 1) / frame_size + 1;
	}

	public int getFrame_size() {
		return frame_size;
	}

	public void setFrame_size(int frame_size) {
		this.frame_size = frame_size;
	}

	
	
	

}