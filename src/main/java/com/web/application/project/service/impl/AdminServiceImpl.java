package com.web.application.project.service.impl;


import com.web.application.project.annotation.Search;
import com.web.application.project.annotation.SearchParam;
import com.web.application.project.dao.AdminDao;
import com.web.application.project.entity.Admin;
import com.web.application.project.entity.Role;
import com.web.application.project.service.AdminService;
import com.web.application.project.shiro.ShiroRealm;
import com.web.application.tools.DesBase64Tool;
import com.web.application.tools.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 管理员管理ServiceImpl
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Resource
    private ShiroRealm shiroRealm;

    @Override
    public Result<List<Admin>> getAdminList(Integer begin, Integer pageSize) {
        Result<List<Admin>> result = new Result<List<Admin>>();
        List<Admin> adminList = adminDao.getAdminList(begin,pageSize);
        if(adminList==null||adminList.size()==0){
            result.setStatus(0);
            result.setMsg("未加载到任何管理员");
        }else{
            result.setCount(adminDao.getAdminListCount());
            result.setPageSize(pageSize);
            result.setStatus(1);
            result.setData(adminList);
        }
        return result;
    }

    @Override
    public Result<Map<String,Object>> getAdminInitList() {
        Result<Map<String,Object>> result = new Result<>();
        List<Map<String,Object>> roleList = adminDao.getAdminRoleList();
        List<String> departmentList = adminDao.getAdminDepartmentList();
        if(roleList==null||roleList.size()==0||departmentList==null||departmentList.size()==0){
            result.setStatus(0);
        }else{
            Map<String,Object> resultMap=new HashMap<>((int)(2/0.7));
            checkRoleListBySuperAdmin(roleList);
            if(roleList.size()==0){
                result.setStatus(0);
            }else{
                result.setStatus(1);
                resultMap.put("roles",roleList);
                resultMap.put("departments",departmentList);
                result.setData(resultMap);
            }
        }
        return result;
    }

    @Override
    @Search
    public Result<List<Admin>> getSearchAdminList(Integer roleId, @SearchParam Admin admin) {
        Result<List<Admin>> result = new Result<List<Admin>>();
        admin.setAccount(admin.getAccount());
        admin.setName(admin.getName());
        List<Admin> adminList = adminDao.getSearchAdminList(roleId,admin);
        if(adminList==null||adminList.size()==0){
            result.setStatus(0);
            result.setMsg("未加载到任何管理员");
        }else{
            result.setCount(adminDao.getSearchAdminCount(roleId,admin));
            result.setPageSize(admin.getPageSize());
            result.setStatus(1);
            result.setData(adminList);
        }
        return result;
    }

    @Override
    @Transactional
    public Result<String> updateAdminLockStatus(String isLocked, Integer adminId,String name) {
        Result<String> result = new Result<String>();
        if(!checkSuperAdmin(adminId)){
            result.setStatus(0);
            result.setMsg("对不起,不允许操作!");
            return result;
        }
        boolean updateResult=adminDao.updateAdminLockStatus(isLocked,adminId);
        if(updateResult){
            shiroRealm.clearCached();
            result.setStatus(1);
            if("Y".equals(isLocked)){
                result.setMsg("已锁定");
                if(name!=null&&!"".equals(name)){
                    shiroRealm.kickOutByUserName(name);
                }
            }else{
                result.setMsg("未锁定");
            }
            result.setData(isLocked);
        }else{
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
        }
        return result;
    }

    @Override
    @Transactional
    public Result removeAdmin(List<Integer> adminIdList,List<String> adminNameList) {
        Result result = new Result();
        for(Integer adminId:adminIdList){
            if(!checkSuperAdmin(adminId)){
                result.setStatus(0);
                result.setMsg("对不起,不允许操作!");
                return result;
            }
        }
        if(adminIdList.size()==0||adminNameList.size()==0){
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
            return result;
        }
        for(int i = 0;i<adminIdList.size();i++){
            adminDao.removeAdminById(adminIdList.get(i));
            shiroRealm.kickOutByUserName(adminNameList.get(i));
        }
        result.setStatus(1);
        result.setMsg("成功删除管理员!");
        return result;
    }

    @Override
    public Result checkAdminAccount(String account) {
        Result result = new Result();
        int count=adminDao.getAdminCountByAccount(account);
        if(count ==0){
            result.setStatus(1);
            result.setMsg("账号可以使用");
        }else{
            result.setStatus(0);
            result.setMsg("账号已存在");
        }
        return result;
    }

    @Override
    public Result<Object> getAdminDetail(Integer adminId) {
        Result<Object> result = new Result<>();
        if(!checkSuperAdmin(adminId)){
            result.setStatus(0);
            result.setMsg("对不起,不允许操作!");
            return result;
        }
        Admin admin = adminDao.getAdminById(adminId);
        List<Map<String,String>> roles = adminDao.getRoleById(adminId);
        Map<String,Object> resultMap = new HashMap<>((int)(2/0.7));
        resultMap.put("admin",admin);
        resultMap.put("roles",roles);
        result.setStatus(1);
        result.setData(resultMap);
        return result;
    }

    @Override
    @Transactional
    public Result saveAdmin(Admin admin,Set<Integer> roles,String oper) {
        Result result = new Result();
        if(admin.getPassword()!=null&&!"".equals(admin.getPassword())){
            admin.setPassword(DesBase64Tool.desEncrypt(admin.getPassword()));
        }
        if("add".equals(oper)){
            adminDao.createAdmin(admin);
            for(Integer roleId:roles){
                adminDao.createAdminRole(admin.getAdmin_id(),roleId);
            }
            result.setStatus(1);
            result.setMsg("增加管理员成功!");
        }else if("update".equals(oper)){
            if(!checkSuperAdmin(admin.getAdmin_id())){
                result.setStatus(0);
                result.setMsg("对不起,不允许操作!");
                return result;
            }
            adminDao.updateAdmin(admin);
            adminDao.removeAdminRoleById(admin.getAdmin_id());
            for(Integer roleId:roles){
                adminDao.createAdminRole(admin.getAdmin_id(),roleId);
            }
            if(admin.getPassword()!=null&&!"".equals(admin.getPassword())){
                shiroRealm.clearCached();
                shiroRealm.kickOutByUserName(admin.getTokenKey());
            }else if(admin.getAccount()!=null&&!"".equals(admin.getAccount())&&!admin.getAccount().equals(admin.getTokenKey())){
                shiroRealm.clearCached();
                shiroRealm.kickOutByUserName(admin.getTokenKey());
            }
            result.setStatus(1);
            result.setMsg("修改管理员成功!");
        }else{
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
        }
        return result;
    }

    @Override
    public Result getRoleList(Role role) {
        Result result = new Result<>();
        List<Map<String,Object>> roleList = adminDao.getRoleList(role.getBegin(),role.getPageSize());
        List<Map<String,Object>> totalRoleList=adminDao.getAdminRoleList();
        checkRoleListBySuperAdmin(roleList);
        checkRoleListBySuperAdmin(totalRoleList);
        if(roleList==null||roleList.size()==0){
            result.setStatus(0);
            result.setMsg("未加载到任何角色");
            return result;
        }
        for(Map<String,Object> map:roleList){
            Integer roleId= (Integer) map.get("role_id");
            List<Map<String,String>> actionList=adminDao.getAllActionByRoleId(roleId);
            List<String> actionNameList = new ArrayList<>();
            for(Map<String,String> actionMap :actionList){
                actionNameList.add(actionMap.get("action_name"));
            }
            map.put("actions",actionNameList);
        }
        result.setStatus(1);
        result.setData(roleList);
        result.setCount(totalRoleList.size());
        result.setPageSize(role.getPageSize());
        return result;
    }

    @Override
    public Result checkRoleName(String roleName) {
        Result result = new Result();
        Integer count=adminDao.getRoleCountByRoleName(roleName);
        if(count==0){
            result.setStatus(1);
        }else{
            result.setStatus(0);
            result.setMsg("角色名称已存在!");
        }
        return result;
    }

    @Override
    public Result getAllAction() {
        Result result = new Result();
        List<Map<String,Object>> actionList = adminDao.getAllAction();
        if(actionList==null||actionList.size()==0){
            result.setStatus(0);
            result.setMsg("未加载到任何权限");
        }else{
            result.setStatus(1);
            result.setData(actionList);
        }
        return result;
    }

    @Override
    public Result getRoleDetail(Integer roleId) {
        Result result = new Result();
        List<Map<String,String>> actionList=adminDao.getAllActionByRoleId(roleId);
        if(actionList==null||actionList.size()==0){
            result.setStatus(0);
            result.setMsg("未加载到相关角色信息");
            return result;
        }
        result.setStatus(1);
        result.setData(actionList);
        result.setMsg(adminDao.getRoleFlagByRoleId(roleId));
        return result;
    }

    @Override
    public Result flagCheck(String flagName) {
        Result result = new Result();
        Integer count=adminDao.getRoleFlagCountByFlagName(flagName);
        if(count==0){
            result.setStatus(1);
        }else{
            result.setStatus(0);
            result.setMsg("角色标记已存在!");
        }
        return result;
    }

    @Override
    @Transactional
    public Result removeRole(List<Integer> roleList) {
        Result result = new Result();
        for(Integer roleId:roleList){
            Integer count=adminDao.getAdminCountByRoleId(roleId);
            if(count!=0){
                result.setStatus(0);
                result.setMsg("该角色正在使用中,无法删除!");
                return result;
            }
        }
        for(Integer roleId:roleList){
            adminDao.removeRoleById(roleId);
        }
        shiroRealm.clearCached();
        result.setStatus(1);
        result.setMsg("删除成功!");
        return result;
    }

    @Override
    @Transactional
    public Result saveRole(Role role, Set<Integer> actions,String oper) {
        Result result = new Result();
        if("add".equals(oper)){
            adminDao.createRole(role);
            for(Integer actionId:actions){
                adminDao.createActionByRoleId(role.getRole_id(),actionId);
            }
            result.setStatus(1);
            result.setMsg("添加角色成功!");
        }else if("update".equals(oper)){
            adminDao.updateRole(role);
            adminDao.removeActionByRoleId(role.getRole_id());
            for(Integer actionId:actions){
                adminDao.createActionByRoleId(role.getRole_id(),actionId);
            }
            shiroRealm.clearCached();
            result.setStatus(1);
            result.setMsg("修改角色成功!");
        }else{
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
        }
        return result;
    }

    /**
     * 检查超级是否是超级管理员
     * @param adminId
     * @return 如果是超级管理员则返回true,如果不是超级管理员并且要操作超级管理员则返回false
     */
    private boolean checkSuperAdmin(Integer adminId){
        boolean isSuperAdmin = SecurityUtils.getSubject().hasRole("super_admin");
        if(!isSuperAdmin){
            List<Map<String,String>> list= adminDao.getRoleById(adminId);
            for(Map<String,String> map:list){
                if("super_admin".equals(map.get("role_flag"))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据是否是超级管理员筛选角色列表
     * @param roleList
     * @return
     */
    private void checkRoleListBySuperAdmin(List<Map<String,Object>> roleList){
        if(roleList==null||roleList.size()==0){
            return;
        }
        if(!SecurityUtils.getSubject().hasRole("super_admin")){
            for(int i=0;i<roleList.size();i++){
                if("super_admin".equals(roleList.get(i).get("role_flag"))){
                    roleList.remove(roleList.get(i));
                }
            }
        }else{
            for(Map<String,Object> map :roleList){
                map.remove("role_flag");
            }
        }
    }

}
