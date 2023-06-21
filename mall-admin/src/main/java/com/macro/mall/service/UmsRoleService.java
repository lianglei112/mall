package com.macro.mall.service;

import com.macro.mall.model.UmsMenu;
import com.macro.mall.model.UmsResource;
import com.macro.mall.model.UmsRole;

import java.util.List;

public interface UmsRoleService {

    List<UmsRole> getRoleList(Long adminId);

    int updateRole(Long adminId, List<Long> roleIds);

    List<UmsRole> listAll();

    int create(UmsRole umsRole);

    List<UmsRole> list(String keyword, Integer pageNum, Integer pageSize);

    int update(Long id, UmsRole umsRole);

    UmsRole getItem(Long id);

    int delete(List<Long> ids);

    List<UmsMenu> listMenu(Long roleId);

    List<UmsResource> listResource(Long roleId);

    int allocMenu(Long roleId, List<Long> menuIds);

    int allocResource(Long roleId, List<Long> resourceIds);

}
