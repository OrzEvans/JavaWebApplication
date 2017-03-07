package com.web.application.project.dao;

import com.web.application.project.entity.Admin;
import com.web.application.project.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 管理员Dao
 */
@Repository
public interface AdminDao {
    /**
     * 获取管理员列表数据
     * @param begin
     * @param pageSize
     * @return
     */
    public List<Admin> getAdminList(@Param("begin") Integer begin, @Param("pageSize") Integer pageSize);

    /**
     * 获取管理员总数
     * @return
     */
    public Integer getAdminListCount();

    /**
     * 获取所有管理员的角色名称
     * @return
     */
    public List<Map<String,Object>> getAdminRoleList();

    /**
     * 获取所有部门名称
     * @return
     */
    public List<String> getAdminDepartmentList();

    /**
     * 获取查询的管理员信息
     * @param roleId
     * @param admin
     */
    public List<Admin> getSearchAdminList(@Param("roleId") Integer roleId, @Param("admin") Admin admin);

    /**
     * 获取搜索的管理员总数
     * @param roleId
     * @param admin
     * @return
     */
    public Integer getSearchAdminCount(@Param("roleId") Integer roleId, @Param("admin") Admin admin);

    /**
     * 更新管理员锁定状态
     * @param isLocked
     * @param adminId
     * @return
     */
    public boolean updateAdminLockStatus(@Param("isLocked") String isLocked, @Param("adminId") Integer adminId);

    /**
     * 根据管理员id删除管理员
     * @param adminId
     * @return
     */
    public boolean removeAdminById(Integer adminId);

    /**
     * 获取指定账号的管理员个数
     * @param account
     * @return
     */
    public Integer getAdminCountByAccount(String account);

    /**
     * 根据管理员id获取管理员详情
     * @param adminId
     * @return
     */
    public Admin getAdminById(Integer adminId);

    /**
     * 根据管理员id查询出该用户对应的所有角色
     * @param adminId
     * @return
     */
    public List<Map<String,String>> getRoleById(Integer adminId);

    /**
     * 增加管理员
     * @param admin
     * @return
     */
    public boolean createAdmin(Admin admin);

    /**
     * 更新管理员
     * @param admin
     * @return
     */
    public boolean updateAdmin(Admin admin);

    /**
     * 删除管理员角色
     * @param adminId
     * @return
     */
    public boolean removeAdminRoleById(Integer adminId);

    /**
     * 添加管理员角色
     * @param adminId
     * @param roleId
     * @return
     */
    public boolean createAdminRole(@Param("adminId") Integer adminId, @Param("roleId") Integer roleId);

    /**
     * 查询出指定角色ID的所有权限
     * @param roleId
     * @return
     */
    public List<Map<String,String>> getAllActionByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取所有角色名称
     * @param begin
     * @param pageSize
     * @return
     */
    public List<Map<String,Object>> getRoleList(@Param("begin") Integer begin, @Param("pageSize") Integer pageSize);

    /**
     * 获取指定角色名称的个数
     * @param roleName
     * @return
     */
    public Integer getRoleCountByRoleName(@Param("roleName") String roleName);

    /**
     * 获取所有权限
     * @return
     */
    public List<Map<String,Object>> getAllAction();

    /**
     * 获取指定角色id的角色标记
     * @param roleId
     * @return
     */
    public String getRoleFlagByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取指定角色标记的个数
     * @param roleFlag
     * @return
     */
    public Integer getRoleFlagCountByFlagName(@Param("roleFlag") String roleFlag);

    /**
     * 查询拥有指定角色的用户个数
     * @param roleId
     * @return
     */
    public Integer getAdminCountByRoleId(@Param("roleId") Integer roleId);

    /**
     * 删除角色
     * @param roleId
     */
    public void removeRoleById(@Param("roleId") Integer roleId);

    /**
     * 增加角色
     * @param role
     */
    public void createRole(Role role);

    /**
     * 更新角色
     * @param role
     */
    public void updateRole(Role role);

    /**
     * 删除指定角色的所有权限
     * @param roleId
     */
    public void removeActionByRoleId(@Param("roleId") Integer roleId);

    /**
     * 增加角色的权限
     * @param roleId
     * @param actionId
     */
    public void createActionByRoleId(@Param("roleId") Integer roleId, @Param("actionId") Integer actionId);

}
