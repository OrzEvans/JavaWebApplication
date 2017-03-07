package com.web.application.project.controller;

import com.web.application.project.entity.Admin;
import com.web.application.project.entity.Role;
import com.web.application.project.service.AdminService;
import com.web.application.tools.Result;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 管理员管理Controller
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    private Logger logger = Logger.getLogger(AdminController.class);

    /**
     * 加载管理员列表
     * @param model
     * @param admin
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/list")
    @RequiresPermissions(value = {"admin:list"})
    public String toAdminList(Model model,Admin admin ){
        Result<List<Admin>> result =adminService.getAdminList(admin.getBegin(),admin.getPageSize());
        model.addAttribute("result",result);
        return "jsp/adminList";
    }

    /**
     * 加载所有角色名称
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    @RequiresPermissions(value = {"admin:list"})
    public Result<Map<String,Object>> getAdminRoleList(){
        return adminService.getAdminInitList();
    }

    /**
     * 加载搜索管理员页及分页
     * @param model
     * @param role
     * @param admin
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:list"})
    public String toSearchAdminList(Model model,String role,Admin admin){
        Result<List<Admin>> result=new Result<List<Admin>>();
        Integer roleId=null;
        try{
            roleId=Integer.valueOf(role);
            roleId=roleId==0?null:roleId;
            String department =admin.getDepartment();
            if("0".equals(department)){
                admin.setDepartment(null);
            }
            result =adminService.getSearchAdminList(roleId,admin);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(0);
            result.setMsg("未加载到任何管理员");
            logger.error("[查询管理员参数异常]"+e);
        }
        model.addAttribute("role",role);
        model.addAttribute("admin",admin);
        model.addAttribute(result);
        return "jsp/adminList";
    }

    /**
     * 锁定/解锁管理员
     * @param status
     * @param id
     * @param name
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/lock",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:edit"})
    public Result<String> updateAdminLockedStatus(String status,String id,String name){
        Result<String> result =new Result<>();
        Integer adminId=null;
        String newStatus=null;
        //过滤状态参数,并转换为新状态
        if(!"Y".equals(status)&&!"N".equals(status)){
            result.setStatus(2);
            logger.error("[解锁/锁定管理员参数不正确]");
            return result;
        }else if ("Y".equals(status)){
            newStatus="N";
        }else{
            newStatus="Y";
        }
        //过滤id参数
        try{
            adminId=Integer.valueOf(id);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(3);
            logger.error("[管理员id参数不正确]"+e);
            return result;
        }
        String principal=(String)SecurityUtils.getSubject().getPrincipal();
        if(principal.equals(name)){
            result.setStatus(4);
            result.setMsg("不允许操作当前账号!");
            return result;
        }
        result = adminService.updateAdminLockStatus(newStatus,adminId,name);
        return result;
    }

    /**
     * 删除管理员
     * @param ids
     * @param name
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:remove"})
    public Result removeAdmin(String ids,String name){
        Result result = new Result();
        if(ids==null||"".equals(ids)||name==null||"".equals(name)){
            result.setMsg("操作失败,请联系管理员!");
            result.setStatus(0);
            logger.error("[删除管理员时参数为空]");
            return result;
        }

        try {
            String idArr[] = ids.split("-");
            String nameArr[] = name.split(",");
            String principal=(String)SecurityUtils.getSubject().getPrincipal();
            for(String str:nameArr){
                if(principal.equals(str)){
                    result.setStatus(4);
                    result.setMsg("不允许操作当前账号!");
                    return result;
                }
            }
            List<Integer> adminIdList = new ArrayList<>();
            List<String> adminNameList = new ArrayList<>();
            for(int i=0;i<idArr.length;i++){
                adminIdList.add(Integer.valueOf(idArr[i]));
                adminNameList.add(nameArr[i]);
            }
            if(adminIdList.size()==adminNameList.size()){
                result=adminService.removeAdmin(adminIdList,adminNameList);
            }else{
                result.setMsg("操作失败,请联系管理员!");
                result.setStatus(0);
                logger.error("[删除管理员时,管理员id与管理员名称不一致]");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("操作失败,请联系管理员!");
            result.setStatus(0);
            logger.error("[删除管理员时出现异常]"+e);
        }
        return result;
    }

    /**
     * 查询管理员账号是否存在
     * @param account
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/check",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:list"})
    public Result checkAdminAccount(String account){
        Result result = new Result();
        if(account==null||"".equals(account)){
            result.setStatus(0);
            result.setMsg("请输入账号");
            return result;
        }
        result = adminService.checkAdminAccount(account);
        return result;
    }

    /**
     * 保存管理员
     * @param admin
     * @param role
     * @return
     */
    @RequiresAuthentication
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:edit","admin:add"})
    public Result saveAdmin (Admin admin,String[] role,String info,String def,String oper){
        Result result = new Result();
        Set<Integer> roles = new HashSet<>();
        try{
            for(String str:role){
                roles.add(Integer.valueOf(str.trim()));
            }
            if(info!=null&&!"".equals(info)){
                admin.setAdmin_id(Integer.valueOf(info));
                admin.setTokenKey(def);
            }
            result=adminService.saveAdmin(admin,roles,oper.trim());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[保存管理员出现异常]"+e);
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
        }
        return result;
    }

    /**
     * 获取管理员详细信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:list"})
    public Result<Object> getAdminDetail(String id){
        Result<Object> result=new Result<>();
        int adminId=0;
        try{
            if(id!=null||!"".equals(id.trim())){
                adminId=Integer.valueOf(id.trim());
                result=adminService.getAdminDetail(adminId);
            }else{
                result.setStatus(0);
                result.setMsg("操作失败,请联系管理员!");
                logger.error("[获取管理员信息时,管理员id为空]");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
            logger.error("[获取管理员信息发生错误]"+e);
        }
        return result;
    }

    /**
     * 跳转角色管理页
     * @param model
     * @param role
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/role")
    @RequiresPermissions(value = {"admin:list"})
    public String toPermissionsList(Model model, Role role){
        Result result = adminService.getRoleList(role);
        model.addAttribute("result",result);
        return "jsp/roleList";
    }

    /**
     * 检查角色名称是否存在
     * @param role
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/roleCheck",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:list"})
    public Result checkRole(String role){
        Result result = new Result();
        if(role==null||"".equals(role.trim())){
            result.setStatus(0);
            result.setMsg("请输入角色名称");
            return result;
        }else{
            result=adminService.checkRoleName(role.trim());
        }
        return result;
    }

    /**
     * 获取权限初始化信息
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/actionInit",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"admin:list"})
    public Result actionInit(){
        return adminService.getAllAction();
    }

    /**
     * 获取角色详情
     * @param id
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/roleDetail",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:list"})
    public Result roleDetail(String id){
        Result result = new Result();
        Integer roleId = 0;
        try{
            roleId= Integer.valueOf(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[获取角色详情出现异常]"+e);
            result.setStatus(0);
            return result;
        }
        result = adminService.getRoleDetail(roleId);
        return result;
    }

    /**
     * 检查角色标记是否已存在
     * @param name
     * @return
     */
    @RequiresAuthentication
    @ResponseBody
    @RequestMapping(value = "/flagCheck",method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:list"})
    public Result flagCheck(String name){
        Result result =new Result();
        if(name==null||"".equals(name)){
            result.setStatus(0);
            result.setMsg("请输入角色标记");
        }else{
            result=adminService.checkRoleName(name.trim());
        }
        return result;
    }

    /**
     * 保存角色
     * @param role
     * @param action
     * @param info
     * @param oper
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "saveRole",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"admin:edit","admin:add"})
    public Result saveRole(Role role, String[] action, String info,String oper){
        Result result = new Result();
        Set<Integer> actions = new HashSet<>();
        try{
            for(String str:action){
                actions.add(Integer.valueOf(str.trim()));
            }
            if(info!=null&&!"".equals(info)){
                role.setRole_id(Integer.valueOf(info));
            }
            result=adminService.saveRole(role,actions,oper.trim());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[保存管理员出现异常]"+e);
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员!");
        }
        return result;
    }

    /**
     * 删除管理员角色
     * @param ids
     * @return
     */
    @RequiresAuthentication
    @ResponseBody
    @RequestMapping(value = "/removeRole",method = RequestMethod.POST)
    @RequiresPermissions(value = {"admin:remove"})
    public Result roleRemove(String ids){
        Result result = new Result();
        if(ids==null||"".equals(ids)){
            result.setStatus(0);
            result.setMsg("请选择角色");
            return result;
        }
        List<Integer> roleList= new ArrayList();
        try{
            String[] idArr = ids.split("-");
            for(String str :idArr){
                roleList.add(Integer.valueOf(str));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[删除角色时出现异常]"+e);
            result.setStatus(0);
            result.setMsg("操作失败,请联系管理员");
            return result;
        }
        result=adminService.removeRole(roleList);
        return result;
    }
}
