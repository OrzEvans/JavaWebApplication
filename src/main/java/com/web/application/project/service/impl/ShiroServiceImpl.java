package com.web.application.project.service.impl;

import com.web.application.project.dao.ShiroDao;
import com.web.application.project.entity.Admin;
import com.web.application.project.service.ShiroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ShiroService实现类
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    @Resource
    private ShiroDao shiroDao;

    @Override
    public Admin getAdminByAccount(String account){
        return shiroDao.findByAccount(account);

    }

    @Override
    public Map<String, Object> listAuthByAdminId(Integer adminId){
        Map<String,Object> map = new HashMap<>();
        map.put("allRoles", shiroDao.findAllRoleByAdminId(adminId));
        map.put("allActions", shiroDao.findAllActionByAdminId(adminId));
        return map;
    }

    @Override
    public void updateLastLoginTime(Integer adminId, String lastLoginTime) {
        shiroDao.updateLastLoginTime(adminId,lastLoginTime);
    }
}
