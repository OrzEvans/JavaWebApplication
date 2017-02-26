<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.base.com/tags-page" prefix="base"%>
<%
    /* 路径相关的变量 */
    //      /Xnl
    String path = request.getContextPath();
    //      http://idouya.net:8080
    String rootPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort();
    //      http://idouya.net:8080/Xnl
    String basePath = rootPath + path;
%>
<base href="<%=basePath%>/"/>
<meta charset="UTF-8">
<meta name="description" content="描述">
<meta name="author" content="作者">
<meta name="keyword" content="关键字">
<link rel="shortcut icon" href="favicon.gif">
<meta http-equivmetahttp-equiv="x-ua-compatible"content="IE=7"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<script type="text/javascript">
    var path = "<%=path%>";var basePath = "<%=basePath%>";var rootPath = "<%=rootPath%>";
</script>
<link rel="stylesheet" href="static/basic/css/base.css" />
<link rel="stylesheet" href="static/basic/css/backstage.css" />
<link rel="stylesheet" href="static/basic/myAlert/myAlert.css" type="text/css">
<link rel="stylesheet" href="static/basic/kindEditor/themes/default/default.css" type="text/css">
<link rel="stylesheet" href="static/basic/font-awesome/css/font-awesome.min.css" type="text/css">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
<!--[if lt IE 9]>
<script src="static/basic/js/html5shiv.js"></script>
<script src="static/basic/js/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="static/basic/js/jquery-3.0.0.min.js" ></script>
<script type="text/javascript" src="static/basic/myAlert/myAlert.js"></script>
<script type="text/javascript" src="static/basic/util/util.js" ></script>
