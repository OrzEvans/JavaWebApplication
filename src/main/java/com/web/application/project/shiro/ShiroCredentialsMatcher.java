package com.web.application.project.shiro;

import com.web.application.tools.DesBase64Tool;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义认证匹配器,进行加密密码校验
 */
public class ShiroCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //取得原始输入数据信息
        String tokenCredentails = DesBase64Tool.desEncrypt(new String(super.toString(token.getCredentials()).getBytes()));
        String dbCredentails = (String)super.getCredentials(info);
        return super.equals(tokenCredentails, dbCredentails);
    }
}
