package com.web.application.project.shiro;

import com.web.application.project.entity.Admin;
import com.web.application.project.service.ShiroService;
import com.web.application.tools.DesBase64Tool;
import com.web.application.tools.DevelopTools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Realm
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource(name = "shiroServiceImpl")
    private ShiroService shiroService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal() ;	// 取得用户登录名
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo() ;	// 定义授权信息的返回数据
        try{
            Integer adminId= shiroService.getAdminByAccount(username).getAdmin_id();
            Map<String,Object> map = shiroService.listAuthByAdminId(adminId) ;
            Set<String> allRoles = (Set<String>) map.get("allRoles") ;
            Set<String> allActions = (Set<String>) map.get("allActions") ;
            auth.setRoles(allRoles);// 所有的角色必须以Set集合的形式出现
            auth.setStringPermissions(allActions); 	// 所有的权限必须以Set集合的形式出现
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName=(String) authenticationToken.getPrincipal();
        Admin admin = shiroService.getAdminByAccount(userName);
        if(admin.getAdmin_id()==null){
            throw new UnknownAccountException("账号不存在");
        }else{
            String password = DesBase64Tool.desEncrypt(new String((char[]) authenticationToken.getCredentials()));
            if(admin.getPassword().equals(password)){
                AuthenticationInfo auth=new SimpleAuthenticationInfo(userName,password,"ShiroRealm");
                shiroService.updateLastLoginTime(admin.getAdmin_id(), DevelopTools.getFormatedDateString(8));
                return auth;
            }else{
                throw new IncorrectCredentialsException("密码错误");
            }
        }
    }

    /**
     * 清空缓存
     */
    public void clearCached(){
        PrincipalCollection principals=SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
        super.clearCachedAuthenticationInfo(principals);
        super.clearCachedAuthorizationInfo(principals);
    }
}
