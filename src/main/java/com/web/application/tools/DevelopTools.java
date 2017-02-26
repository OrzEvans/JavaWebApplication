/**
 * 文件名称:DevelopTools.java 创建者:Evans 创建日期:2017年1月11日
 */
package com.web.application.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DevelopTools
 * @Title  开发工具类
 * @author Evans
 * @date 2017年1月11日
 * @version 1.0
 */
public class DevelopTools {
	
	/**
	 * 根据时区获取时间
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * @author Jeff
	 * @param timeZoneOffset
	 * @return
	 */
	public static String getFormatedDateString(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }
	/**
	 * 设置模糊查询参数
	 * @param parameter 模糊查询参数
	 * @return 拼接%后的参数
	 */
	public static String getLikeParameter(String parameter){
		if(parameter ==null||"".equals(parameter)){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("%").append(parameter.trim()).append("%");
		return sb.toString();
	}
	/**
	 * 去掉模糊查询参数
	 * @param parameter 带%的参数
	 * @return 去掉%后的参数
	 */
	public static String delLikeParameter(String parameter){
		if(parameter ==null||"".equals(parameter)){
			return null;
		}
		parameter = parameter.replace("%", "");
		return parameter;
	}
	/**
	 * 获取真实ip地址
	 * @param request HttpServletRequest
	 * @return
	 * @author Evans
	 */
	public static String getIpAddress(HttpServletRequest request) {  
		 String ip = request.getHeader("x-forwarded-for");  
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		     ip = request.getHeader("Proxy-Client-IP");  
		 }  
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		     ip = request.getHeader("WL-Proxy-Client-IP");  
		 }  
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//		     ip = request.getRemoteAddr();
			 ip = request.getHeader("X-Real-IP");
		 }  
		 return ip;  
	} 
	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: Jeff
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @throws IOException 
	*/
	public static void generateImage(String imgStr, String path) throws IOException {
		if (imgStr == null)return ;
		File file=new File(path);
		if(!file.exists()){
			BASE64Decoder decoder = new BASE64Decoder();
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
//			System.out.println(b);
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
		}
	}
	/**
	 * 生成二维码
	 * @param dirs 存放二维码的路径
	 * @param fileName 二维码文件名，不包含扩展名(默认扩展名为jpg)
	 * @param str 要转换为二维码的字符串
	 * @return 生成成功返回true，若有异常则返回false
	 */
	public  boolean createQRCode(String dirs,String fileName,String str){
		try{
			File dir = new File(dirs);
			if(!dir.exists()){
				dir.mkdirs();
			}
			// 图像宽度
			int width = 200; 
			// 图像高度
	        int height = 200;  
	        // 图像类型  
	        String format = "jpg";
	        //使用google的zxing包生成二维码图片
	        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	        // 生成矩阵  
	        BitMatrix bitMatrix;
				bitMatrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, width, height, hints);
	        //拼接二维码图片保存路径
	        String path = dirs+"/"+fileName+"."+format;
	        // 输出图像 
	        MatrixToImageWriter.writeToFile(bitMatrix, format, new File(path));
	        //返回二维码下载地址（访问地址）
		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
        return true;
	}
	/**
	 * 读取文件内容
	 * @param path
	 * @author Mikey
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static String readFileContent(String path) throws IOException{
		StringBuffer sb = new StringBuffer();
		String str = "" ;
		File file = new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		while((str=br.readLine())!=null){
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * 写入文件内容
	 * @param path
	 * @param obj
	 * @author Mikey
	 * @throws IOException
	 */
	public static void writeFileContent(String path,JSONObject obj) throws IOException{
		File file = new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		Writer out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
//		FileOutputStream fos = new FileOutputStream(file);
//		OutputStreamWriter out = new OutputStreamWriter(fos);
		out.write(obj.toString());
		out.close();
	}
	/**
	 * ContainSpecialKeyWord
	 * @Title  判断字符串中是否有特殊关键字
	 * @param str String
	 * @return boolean 包含特殊关键字返回true，否则返回false
	 * @author Faker
	 * <hr/>
	 * @Modify 修改人:Evans 修改日期: 2017年1月11日
	 * <li>修改方法名称，去除多余单行注释，增加文档注释</li>
	 */
	public static boolean ContainSpecialKeyWord(String str) {
		String inj_str = "'|xor|/**/|exec|insert|select|delete|update|count|NAME_CONST|;|collate|name_const|COLLATE";
		// :|>|<|--|sp_|xp_|\|dir|cmd|^|(|)|$|copy|format|%
		boolean flag = false;
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.toLowerCase().indexOf(inj_stra[i]) >= 0) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * filterSpecialSymbol
	 * 过滤输入字符串的特殊符号
	 * @param str 输入的字符串
	 * @return 返回替换掉特殊字符的新字符串
	 * @Faker
	 * <hr/>
 	 * @Modify 修改人: Evans 修改日期:2017年1月11日
	 * <li>修改方法名称</li>
	 */
	public static String filterSpecialSymbol(String str) {
		if (str == null) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0, c = str.length(); i < c; ++i) {
			char ch = str.charAt(i);
			switch (ch) {
			case '>':
				sb.append("&gt;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&#039;");
				break;
			default:
				sb.append(ch);
				break;
			}

		}
		return sb.toString();

	}
	/**
	 * replceaScriptLabel
	 * @Title  替换script标签
	 * @param str String
	 * @return 返回替换后的字符串
	 * @author Faker
	 * <hr/>
	 * @Modify 修改人:Evans 修改日期: 2017年1月11日
	 * <li>修改方法名称，增加文档注释</li>
	 */
	public static String replceaScriptLabel(String str) {
		if (str == null) {
			return "";
		} else {
			str = str.replaceAll("(?i)<script", "&lt;script");
			str = str.replaceAll("(?i)</script>", "&lt;/script");
			return str;
		}
	}
	/**
	 * 过滤对象中的所有String类型的数据中的符特殊符号 < > & " \
	 * @param t 任意对象，该对象必须存在set和get方法
	 * @return 过滤过特殊符号后的原对象
	 * @author Evans
	 */
	public static <T> T filterSpecialCharacterInObject(T t){
		Class<? extends Object> tClass = t.getClass();
		Field[] fields =tClass.getDeclaredFields();
		Method getMethod = null; 
		Method setMethod = null; 
		for (Field field : fields) {
			Class<?> type = field.getType();
			if(String.class.equals(type)){
				String fieldName=field.getName();
				String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  
		        try {
					getMethod = tClass.getMethod("get" + methodName);
					setMethod = tClass.getMethod("set" + methodName,String.class);
					Object value=getMethod.invoke(t);
					String str = (String)value;
					String newStr= DevelopTools.filterSpecialSymbol(str);
					setMethod.invoke(t, newStr);
				} catch (Exception e) {
					e.printStackTrace();
					return t;
				}
			}
		}
		return t;
	}
	/**
	 * 检测是否有特殊字符
	 * @param str 需要检测的字符串
	 * @return boolean 不含有特殊字符返回false，若含有特殊字符返回true
	 * @author Faker
	 */
	public static boolean checkInputSqlInj(String str){
		if(str.equals(DevelopTools.filterSpecialSymbol(str))){
			return false;
		}
		return true;
	}
}
