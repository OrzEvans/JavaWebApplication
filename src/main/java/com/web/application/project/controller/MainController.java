package com.web.application.project.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

/**
 * 主要逻辑Controller
 * 用于登录退出及首页跳转
 */
@Controller
public class MainController {
    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping(value = {"/login","/"})
    public String toLogin(){

        return "jsp/login";
    }

    /**
     * 登录成功后跳转首页
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = {"/index","/index.*"})
    public String toIndex(){
        return "jsp/index";
    }

    /**
     * 退出操作
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/logout")
    public String toLogout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
