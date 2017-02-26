<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@include file="Util.jsp"%>
<body>
<header class="header">
    <div class="top-left">
        <a href=""><img src="static/basic/img/top/logo.jpg" /></a>
    </div>
    <div class="top-right">
        <ul>
            <li class="tr-username">
                <a href="#">
                    Hi,<shiro:principal/>
                </a>
            </li>

            <li>
                <select class="top-right-drop-down">
                    <option>中文</option>
                </select>
            </li>
            <li>
                <a href="logout"><i class="fa fa-power-off"></i>退出</a>
            </li>
            <!-- user login dropdown end -->
        </ul>
    </div>
</header>
<!--
    描述：菜单
-->
<nav class="nav" id='cssmenu'>
    <ul>
        <li>
            <a href='index'>
                <span>首页</span>
            </a>
        </li>
        <li class='has-sub'>
            <a>
                <span>管理员管理</span>
            </a>
            <ul>
                <li class='has-sub-a'>
                    <a href='admin/list'>资料管理</a>
                </li>
                <li class='has-sub-a'>
                    <a href='admin/role'>权限管理</a>
                </li>
            </ul>
        </li>
        <li class='has-sub'>
            <a href='#'>
                <span>设计管理<b class="em-c-yi">20</b></span>
            </a>
            <ul>
                <li class='has-sub-a'>
                    <a href='payment.shtml'>前端<b class="em-c-er">20</b></a>
                </li>
                <li class='has-sub-a'>
                    <a href='service-management.shtml'>服务费管理</a>
                </li>
            </ul>
        </li>
        <li class='has-sub'>
            <a href='#'>
                <span>字体<b class="em-c-yi">20</b></span>
            </a>
            <ul>
                <li class='has-sub-a'>
                    <a href='payment.shtml'>前端<b class="em-c-er">20</b></a>
                </li>
                <li class='has-sub-a'>
                    <a href='service-management.shtml'>服务费管理</a>
                </li>
            </ul>
        </li>
        <li class='has-sub'>
            <a href='#'>
                <span>素材<b class="em-c-yi">20</b></span>
            </a>
            <ul>
                <li class='has-sub-a'>
                    <a href='payment.shtml'>前端<b class="em-c-er">20</b></a>
                </li>
                <li class='has-sub-a'>
                    <a href='service-management.shtml'>服务费管理</a>
                </li>
            </ul>
        </li>
    </ul>
</nav>
</body>