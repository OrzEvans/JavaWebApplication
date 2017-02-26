<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    /* 路径相关的变量 */
    //      /Xnl
    String path = request.getContextPath();
    //      http://idouya.net:8080
    String rootPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort();
    //      http://idouya.net:8080/Xnl
    String basePath = rootPath + path;
    String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    request.setAttribute("error",error);
%>
<script type="text/javascript">
    var path = "<%=path%>";var basePath = "<%=basePath%>";var rootPath = "<%=rootPath%>";
</script>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="description" content="">
		<meta name="author" content="">
		<meta name="keyword" content="">
		<link rel="stylesheet" href="<%=basePath%>/static/basic/css/login.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>/static/basic/js/jquery-3.0.0.min.js" ></script>
		<script type="text/javascript" src="<%=basePath%>/static/basic/util/util.js" ></script>
		<script type="text/javascript" src="<%=basePath%>/static/scripts/login.js"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
		<!--[if lt IE 9]>
		<script src="<%=basePath%>/static/basic/js/html5shiv.js"></script>
		<script src="<%=basePath%>/static/basic/js/respond.min.js"></script>
		<![endif]-->
		<title>欢迎使用后台管理系统</title>
	</head>
	<body> 
<%-- 整页loading --%>
<div class="loading" style="display:none;">
	<div class="spinner">
		<div class="spinner-container container1">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
		<div class="spinner-container container2">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
		<div class="spinner-container container3">
			<div class="circle1"></div>
			<div class="circle2"></div>
			<div class="circle3"></div>
			<div class="circle4"></div>
		</div>
	</div>
</div>
		<div id="contain">
			<div class="wrapper">
				<div class="logo">
					<h1>LOGIN PAGE</h1>
				</div>
				<div class="lg-body">
		 			<div class="inner">
		 				<div id="lg-head">
							<p><span class="font-bold">Admin</span>: Please login to access the control panel.</p>
						</div>
						<div class="login">
			 				<form id="lg-form" method="post" action="login" onsubmit="return Login.event.loginSubmit();">
			   					<fieldset>
				  					<ul>
					 					<li id="usr-field">
					  						<input class="input required" id="userName" name="username" type="text" size="26" minlength ="1" placeholder="Username..." />
					  						<p id="userNameP"><c:if test="${fn:contains(error, 'AuthenticationException')}">账号不存在</c:if></p>
					 					</li>
					 					<li id="psw-field">
					  						<input class="input required" id="password" name="password" type="password" size="26" minlength="1" placeholder="Password..." />
					  						<p id="passwordP"><c:if test="${fn:contains(error, 'IncorrectCredentialsException')}">密码错误</c:if></p>
					 					</li>

						 				<li>
						  					<input class="submit" type="submit" id="subBtn" value="" />
									 	</li>
				  					</ul>
			   					</fieldset>
			 				 </form>
						</div>
		 			</div>
		 		</div>	
			</div>
		</div>
	</body>
</html>
