package com.macro.mall.dao;

import com.macro.mall.model.UmsAdminRoleRelation;
import com.macro.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色关系管理自定义Dao
 *
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 21:06
 */
public interface UmsAdminRoleRelationDao {

    /**
     * 用于获取所有用户角色
     *
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 批量插入用户角色关系
     *
     * @param umsAdminRoleRelations
     * @return
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> umsAdminRoleRelations);
}
