package com.web.application.tools;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 文件上传工具类<br>
 * 可上传到当前服务器,支持的类型有:
 * <li>image:jpg/jpeg/bmp/png/gif/ico</li>
 * <li>video:mp4/avi/rmvb/wmv</li>
 * <li>file:doc/docx/xls/xlsx/txt/ppt</li>
 * <li>music:mp3</li>
 * <li>css文件</li>
 * @author Evans
 * @version 1.0
 * @since 2016-11-16
 * 
 */
public class FileUploadUtil {
	/**
	 * 上传文件允许的后缀集合
	 */
	private static ArrayList<String> allowedExts = new ArrayList<String>();
	/**
	 * 设置允许后缀的集合
	 * @param type
	 * @author Evans
	 */
	public static void setAllowedExts(String type){
			//图片类型支持的后缀
		if ("image".equalsIgnoreCase(type)) {
			allowedExts.add("jpg");
			allowedExts.add("jpeg");
			allowedExts.add("bmp");
			allowedExts.add("png");
			allowedExts.add("gif");
			allowedExts.add("ico");
			//视频类型支持的后缀
		} else if ("video".equalsIgnoreCase(type)) {
			allowedExts.add("mp4");
			allowedExts.add("avi");
			allowedExts.add("rmvb");
			allowedExts.add("wmv");
			//音乐类型支持的后缀
		} else if ("music".equalsIgnoreCase(type)) {
			allowedExts.add("mp3");
			//文件类型支持的后缀
		} else if ("file".equalsIgnoreCase(type)) {
			allowedExts.add("doc");
			allowedExts.add("docx");
			allowedExts.add("xls");
			allowedExts.add("xlsx");
			allowedExts.add("txt");
			allowedExts.add("ppt");
			//以css文件为后缀的css文件
		} else if ("css".endsWith(type)) {
			allowedExts.add("css");
			//其他不支持的后缀
		}else{
			allowedExts=null;
		}
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
		//基本路径若为null，则改为空字符串
		basePath = basePath==null?"":basePath;
		//保存路径若为null，则改为空字符串
		path = path==null?"":path;
		//若文件大小为0，则返回null
		if(file.getSize()==0){
			return null;
		}
		//按照上传类型设置允许的后缀
		setAllowedExts(uploadType);
		//不支持的后缀则返回null
		if(allowedExts==null){
			return null;
		}
		//获取文件名称
		String fileName = file.getOriginalFilename();
		//获取文件后缀，并小些处理
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		//循环便利后缀名称，若支持则继续，若不支持则返回null
		boolean flag=false;
		for (int i = 0; i < allowedExts.size(); i++) {
			if (allowedExts.get(i).equals(extensionName)) {
				flag = true;
				break;
			}
		}
		//不支持的后缀则返回null
		if(!flag){
			return null;
		}
		//新的文件名 = 获取时间戳+"."+文件扩展名
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String prefix = sdf.format(d);
		String newFileName = String.valueOf(System.currentTimeMillis()) + "."+ extensionName;
		String dir = basePath+path;
		//判断是否需要时间前缀
		if(timePrefix){
			dir += prefix;
		}
		//获取文件保存路径
		String saveFilePath = request.getSession().getServletContext().getRealPath("/")+ dir;
		//创建文件目录
		File fileDir = new File(saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		//上传文件
		try {
			String name = null;
			if(timePrefix){
				name=saveFilePath + "\\" + newFileName;
			}else{
				name = saveFilePath + fileName;
			}
			FileOutputStream out = new FileOutputStream(name);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			// 写入文件
			bos.write(file.getBytes());
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回保存路径的url
		String link = "";
		if(timePrefix){
			link = basePath+path+prefix+"/"+newFileName;
		}else{
			link=basePath+path+fileName;
		}
		return link;
	}
	
}
