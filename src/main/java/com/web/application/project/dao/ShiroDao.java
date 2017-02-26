package com.web.application.project.dao;

import com.web.application.project.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Shiro所使用的Dao,用于进行权限与角色验证
 */
@Repository
public interface ShiroDao {
    /**
     * 进行登录认证使用的,即根据管理员名称取得密码进行认证
     * @param account 账号
     * @return
     */
    public Admin findByAccount(String account);

    /**
     * 根据管理员账号查询出该用户对应的所有角色的标记名称(该标记名称千万不要用中文)
     * @param adminId 管理员ID
     * @return
     */
    public Set<String> findAllRoleByAdminId(Integer adminId);

    /**
     * 查询出一个管理员对应的所有权限数据
     * @param adminId 管理员ID
     * @return
     */
    public Set<String> findAllActionByAdminId(Integer adminId);

    /**
     * 更新管理员最后登录时间
     * @param adminId
     * @param lastLoginTime
     */
    public void updateLastLoginTime(@Param("adminId") Integer adminId, @Param("lastLoginTime") String lastLoginTime);
}
