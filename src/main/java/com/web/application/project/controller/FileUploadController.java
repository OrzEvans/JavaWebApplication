package com.web.application.project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.application.tools.FileUploadUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传工具类<br>
 * 可放置在Controller中用于editor上传
 * <li>请求/editorUpload为上传到服务器</li>
 * <li>请求/editorUploadToOss为上传到阿里云Oss</li>
 * <hr>
 * 可上传到当前服务器或者上传至阿里云,支持的类型有:
 * <li>image:jpg/jpeg/bmp/png/gif/ico</li>
 * <li>video:mp4/avi/rmvb/wmv</li>
 * <li>file:doc/docx/xls/xlsx/txt/ppt</li>
 * <li>music:mp3</li>
 * <li>css文件</li>
 * @author Evans
 * @version 2.0 整合editor上传方法
 * @since 2016-11-19
 * 
 */
@Controller
public class FileUploadController {
	/**
	 * 编辑器的通用保存路径
	 */
	private String EDITOR_BASE_PATH="Static/UploadFile/";
	/**
	 * 编辑器的次级路径
	 */
	private String EDITOR_PATH="";
	/**
	 * 编辑器文件大小限制
	 */
	private Integer EDITOR_MAXSIZE=50000000;
	/**
	 * 国际化语言
	 */
	private ResourceBundle LANG = null;
	/**
	 * editor上传到服务器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Evans
	 */
	@ResponseBody
	@RequestMapping(value="/editorUpload",method=RequestMethod.POST)
	public Map<String,Object> kindEditorUpload(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception{
		return kindEditorUploadUtil(request,response);
	}
	/**
	 * 封装公用的editor上传方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Evans
	 */
	@SuppressWarnings("unused")
	private Map<String,Object> kindEditorUploadUtil(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception{
		LANG =(ResourceBundle)request.getSession().getAttribute("lang");
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/") + EDITOR_BASE_PATH;

		//文件保存目录URL
		String saveUrl  = EDITOR_BASE_PATH;

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv,mp4");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = EDITOR_MAXSIZE;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			
			return getError(getGlobalLanguage(LANG,"请选择文件"));
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			uploadDir.mkdirs();
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return getError(getGlobalLanguage(LANG,"上传目录没有写权限"));
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			return getError(getGlobalLanguage(LANG,"目录名不正确"));
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		Map<String, MultipartFile> fileMap = request.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		Iterator<Entry<String, MultipartFile>> itr = entrySet.iterator();
		String key = "";
		Map<String,Object> obj = new HashMap<String,Object>();
		while (itr.hasNext()) {
			MultipartFile file = itr.next().getValue();
			key=FileUploadController.fileUpload(file, request, "image", EDITOR_BASE_PATH, EDITOR_PATH, true);
			obj.put("error", 0);
			obj.put("url", key);
			}
		return obj;
	}
	/**
	 * 用于封装错误信息用于editor
	 * @param message
	 * @return
	 * @author Evans
	 */
	private Map<String,Object> getError(String message) {
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}

	/**
	 * 文件上传到自有服务器
	 * @param file MultiPartFile 文件
	 * @param request HttpServletRequest
	 * @param uploadType String 上传类型"image","video","music","file","css"
	 * @param basePath String  通用基本路径
	 * @param path String 通用基本路径下的目录
	 * <hr>
	 * 以上两个路径示例:<br>
	 * basePath="Static/UploadFile/"<br>
	 * path="Banner/"<br>
	 * 则上传路径为：项目路径/Static/UploadFile/Banner/<br>
	 * 若为null则路径为""
	 * <hr>
	 * @param timePrefix Boolean 是否需要时间戳
	 * @return 返回上传后的文件路径
	 * @throws IOException 
	 */
	public static String fileUpload(MultipartFile file,HttpServletRequest request,String uploadType,String basePath, String path,boolean timePrefix) throws IOException {
		return FileUploadUtil.fileUpload(file,request,uploadType,basePath,path,timePrefix);
	}
	/**
	 * 将字符串转换国际化文字，若为null则默认使用中文
	 * @param lang ResourceBundle
	 * @param str
	 * @return
	 * @author Evans
	 */
	private String getGlobalLanguage(ResourceBundle lang,String str){
		if(lang==null){
			return str;
		}else{
			return lang.getString(str);
		}
	}
	/**
	 * 设置editor的通用保存路径
	 * @param  eDITOR_BASE_PATH
	 * @author Evans
	 */
	public  void setEDITOR_BASE_PATH(String eDITOR_BASE_PATH) {
		EDITOR_BASE_PATH = eDITOR_BASE_PATH;
	}
	/**
	 * 设置editor的次级保存路径，默认为""
	 * @param 
	 * @author Evans
	 */
	public  void setEDITOR_PATH(String eDITOR_PATH) {
		EDITOR_PATH = eDITOR_PATH;
	}
	/**
	 * 设置编辑器最大上传大小
	 * @param 
	 * @author Evans
	 */
	public void setEDITOR_MAXSIZE(Integer eDITOR_MAXSIZE) {
		EDITOR_MAXSIZE = eDITOR_MAXSIZE;
	}
	/**
	 * 设置国际化默认语言，若不设置则将通过session获取lang
	 * @param lANG
	 */
	public void setLANG(ResourceBundle lANG) {
		LANG = lANG;
	}
}
