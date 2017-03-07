<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@include file="Util.jsp"%>
<style type="text/css">
    /*整页loading*/
    .spinner {
        margin: 350px auto;
        width: 80px;
        height: 80px;
        position: relative;

    }
    .container1> div,
    .container2> div,
    .container3> div {
        width: 16px;
        height: 16px;
        background-color: #CBD0D0;
        border-radius: 100%;
        position: absolute;
        -webkit-animation: bouncedelay 1.2s infinite ease-in-out;
        animation: bouncedelay 1.2s infinite ease-in-out;
        -webkit-animation-fill-mode: both;
        animation-fill-mode: both;
    }

    .spinner .spinner-container {
        position: absolute;
        width: 100%;
        height: 100%;
    }

    .container2 {
        -webkit-transform: rotateZ(45deg);
        transform: rotateZ(45deg);
    }

    .container3 {
        -webkit-transform: rotateZ(90deg);
        transform: rotateZ(90deg);
    }

    .circle1 {
        top: 0;
        left: 0;
    }

    .circle2 {
        top: 0;
        right: 0;
    }

    .circle3 {
        right: 0;
        bottom: 0;
    }

    .circle4 {
        left: 0;
        bottom: 0;
    }

    .container2 .circle1 {
        -webkit-animation-delay: -1.1s;
        animation-delay: -1.1s;
    }

    .container3 .circle1 {
        -webkit-animation-delay: -1.0s;
        animation-delay: -1.0s;
    }

    .container1 .circle2 {
        -webkit-animation-delay: -0.9s;
        animation-delay: -0.9s;
    }

    .container2 .circle2 {
        -webkit-animation-delay: -0.8s;
        animation-delay: -0.8s;
    }

    .container3 .circle2 {
        -webkit-animation-delay: -0.7s;
        animation-delay: -0.7s;
    }

    .container1 .circle3 {
        -webkit-animation-delay: -0.6s;
        animation-delay: -0.6s;
    }

    .container2 .circle3 {
        -webkit-animation-delay: -0.5s;
        animation-delay: -0.5s;
    }

    .container3 .circle3 {
        -webkit-animation-delay: -0.4s;
        animation-delay: -0.4s;
    }

    .container1 .circle4 {
        -webkit-animation-delay: -0.3s;
        animation-delay: -0.3s;
    }

    .container2 .circle4 {
        -webkit-animation-delay: -0.2s;
        animation-delay: -0.2s;
    }

    .container3 .circle4 {
        -webkit-animation-delay: -0.1s;
        animation-delay: -0.1s;
    }

    @-webkit-keyframes bouncedelay {
        0%,
        80%,
        100% {
            -webkit-transform: scale(0.0)
        }
        40% {
            -webkit-transform: scale(1.0)
        }
    }

    @keyframes bouncedelay {
        0%,
        80%,
        100% {
            transform: scale(0.0);
            -webkit-transform: scale(0.0);
        }
        40% {
            transform: scale(1.0);
            -webkit-transform: scale(1.0);
        }
    }
    .loading{
        width: 100%;
        height: 100%;
        margin: auto;
        z-index: 5;
        position: fixed;
        background: rgba(0, 0, 0, 0.52);
        opacity: 1;
        display: none;
        z-index: 9999;
        _position:absolute;
        left:0;
        top:0;
    }
</style>
<body>
<%-- 整页loading --%>
<div class="loading">
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
<header class="header">
    <div class="top-left">
        <a href="index"><img src="static/basic/img/top/logo.jpg" /></a>
    </div>
    <div class="top-right">
        <ul>
            <li class="tr-username">
                <a >
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
        <shiro:hasPermission name="admin:list">
            <li class='has-sub'>
                <a>
                    <span>管理员管理</span>
                </a>
                <ul>
                    <li class='has-sub-a'>
                        <a href='admin/list'>账号管理</a>
                    </li>
                    <li class='has-sub-a'>
                        <a href='admin/role'>权限管理</a>
                    </li>
                </ul>
            </li>
        </shiro:hasPermission>
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