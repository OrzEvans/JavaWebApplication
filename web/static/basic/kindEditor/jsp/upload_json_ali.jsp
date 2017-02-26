<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %> 
<%@ page import="com.aliyun.oss.OSSClient" %>
<%@ page import="com.aliyun.oss.OSSException" %>
<%@ page import="com.aliyun.oss.model.ObjectMetadata" %>
<%@ page import="com.aliyun.oss.model.PutObjectResult" %> 
<%@ page import="java.awt.image.*" %>
<%@ page import="java.awt.Graphics" %>
<%@ page import="java.awt.Component" %>
<%@ page import="java.awt.Canvas" %>
<%@ page import="java.awt.image.AreaAveragingScaleFilter" %>
<%@ page import="java.awt.image.FilteredImageSource" %>
<%
	/**
	 * 阿里云AccessKeyID
	 * 备用<br>
	 * zo0B1fXojFvohK2G
	 */
	final String ACCESSKEY_ID = "YagmaNGk6dl952IL";
	/**
	 * 阿里云AccessKeySecret
	 * 备用<br>
	 * qbEioLiyazwblGFLopAMAIrIMmArp5
	 */
	final String ACCESSKEY_SECRET = "4bqlgZcyWg4bHYo3vSH1BZT3QvtkVn";
	/**
	 * 阿里云EndPoint
	 * 备用<br>
	 * http://oss-cn-qingdao.aliyuncs.com
	 */
	final String END_POINT = "http://oss-cn-hongkong.aliyuncs.com";
	/**
	 * 阿里云Basket
	 */
	final String BASKET = "video-home";
	/**
	 * 阿里云外链的访问路径
	 */
	final String ALI_OSS_URL = "http://video-home.oss-cn-hongkong.aliyuncs.com";
//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/") + "Static/UploadFile/";

//文件保存目录URL
String saveUrl  = "/Static/UploadFile/";

//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv,mp4");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 50000000;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	uploadDir.mkdirs();
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
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

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
String key = "";
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}

		String newFileName =  String.valueOf(System.currentTimeMillis()) + "." + fileExt;
		try{
			//----------------------------------------------------------上传阿里------------------------------------------------------------------------
			File files = new File(savePath + "\\"+ newFileName);
			item.write(files);
			String img=  Arrays.<String>asList(extMap.get("image")).toString();
			//-------------------------------处理图片----------
			/* if(img.contains(fileExt)){
				//得到原图信息
				Image image = ImageIO.read(files); 
				//得到原图的宽、高
				int width = image.getWidth(null); 
				int height = image.getHeight(null); 
				//如果宽度大于某个值，计算高度比例,计算新图片大小
				if(width>750){
	            	width=750;
	            	height = height*width/image.getWidth(null);
	            	//构造目标图片
	        		BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
	        		Graphics graphics = bufferedImage.createGraphics();
	        		//根据需求画出目标图片
	        		graphics.drawImage(image, 0, 0,width,height,null);
	        		//生成新图片
	        		ImageIO.write(bufferedImage, "JPEG", new File(savePath + "\\"+newFileName));
	            }
			} */
			OSSClient client = new OSSClient(END_POINT,ACCESSKEY_ID, ACCESSKEY_SECRET);
			InputStream content1 = new FileInputStream(files);
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(files.length());
			key= "Static/UploadFile"+"/"+dirName+"/"+ymd+"/"+newFileName;
			// 上传Object.
			PutObjectResult results = client.putObject(BASKET,key, content1, meta);
			key = ALI_OSS_URL +"/"+key;
			//-----------------------------------------------------------------------------------------------------------------------------------------
		}catch(Exception e){
			out.println(getError("上传文件失败。"));
			return;
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", key);
		out.println(obj.toJSONString());
	}
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>