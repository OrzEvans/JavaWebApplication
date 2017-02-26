package com.web.application.project.shiro;

import com.web.application.project.entity.Admin;

import java.util.Map;

/**
 * 用于Shiro的Service
 */
public interface ShiroService {
    /**
     * 此方法留给Realm进行用户认证使用的,目的是根据账号获取管理员信息
     * @param account
     * @return
     * @throws Exception
     */
    public Admin getAdminByAccount(String account)  ;

    /**
     * 此方法是留给Realm实现授权处理的,要根据管理员id,查询出所有的角色和所有权限
     * @return 返回的数据包含2个内容<br/>
     * <li>key=allRoles;value=所有角色;<li/>
     * <li>key=allActions;value=所有权限;</li>
     * @throws Exception
     */
    public Map<String,Object> listAuthByAdminId(Integer adminId)  ;

    /**
     * 更新管理员最后登录时间
     * @param adminId
     * @param lastLoginTime
     */
    public void updateLastLoginTime(Integer adminId, String lastLoginTime);
}
