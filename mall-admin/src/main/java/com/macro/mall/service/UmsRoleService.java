package com.macro.mall.service;

import com.macro.mall.model.UmsRole;

import java.util.List;

public interface UmsRoleService {

    List<UmsRole> getRoleList(Long adminId);

    int updateRole(Long adminId, List<Long> roleIds);
}
