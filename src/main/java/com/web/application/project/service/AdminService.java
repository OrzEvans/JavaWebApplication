package com.web.application.project.service;


import com.web.application.project.entity.Admin;
import com.web.application.project.entity.Role;
import com.web.application.tools.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理员管理Service
 */
public interface AdminService {
    /**
     * 获取管理员列表
     * @param begin
     * @param pageSize
     * @return Result<List<Admin>>
     */
    public Result<List<Admin>> getAdminList(Integer begin, Integer pageSize);

    /**
     * 获取管理员页面初始化信息
     * @return
     */
    public Result<Map<String,Object>> getAdminInitList();

    /**
     * 获取查询管理员列表
     * @param roleId
     * @param admin
     * @return
     */
    public Result<List<Admin>> getSearchAdminList(Integer roleId, Admin admin);

    /**
     * 更新管理员锁定状态
     * @param isLocked
     * @param adminId
     * @param name
     * @return
     */
    public Result<String> updateAdminLockStatus(String isLocked, Integer adminId, String name);

    /**
     * 删除管理员操作
     * @param adminIdList
     * @param adminNameList
     * @return
     */
    public Result removeAdmin(List<Integer> adminIdList, List<String> adminNameList);

    /**
     * 检查管理员账号是否存在
     * @param account
     * @return
     */
    public Result checkAdminAccount(String account);

    /**
     * 获取指定管理员的详细信息
     * @param adminId
     * @return
     */
    public Result<Object> getAdminDetail(Integer adminId);

    /**
     * 保存管理员相关信息
     * @param admin
     * @param roles
     * @param oper
     * @return
     */
    public Result saveAdmin(Admin admin, Set<Integer> roles,String oper);

    /**
     * 获取角色列表
     * @return
     */
    public Result getRoleList(Role role);

    /**
     * 检查角色名称是否存在
     * @param roleName
     * @return
     */
    public Result checkRoleName(String roleName);

    /**
     * 获取所有权限
     * @return
     */
    public Result getAllAction();

    /**
     * 获取指定角色的权限信息
     * @param roleId
     * @return
     */
    public Result getRoleDetail(Integer roleId);

    /**
     * 检查指定角色标记是否存在
     * @param flagName
     * @return
     */
    public Result flagCheck(String flagName);

    /**
     * 删除角色
     * @param roleList
     * @return
     */
    public Result removeRole(List<Integer> roleList);

    /**
     * 保存角色
     * @param role
     * @param actions
     * @param oper
     * @return
     */
    public Result saveRole(Role role, Set<Integer> actions,String oper);
}
