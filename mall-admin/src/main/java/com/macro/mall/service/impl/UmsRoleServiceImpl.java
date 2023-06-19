package com.macro.mall.service.impl;

import com.macro.mall.dao.UmsAdminRoleRelationDao;
import com.macro.mall.model.UmsRole;
import com.macro.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 21:06
 */
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsAdminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        
        return 0;
    }
}
