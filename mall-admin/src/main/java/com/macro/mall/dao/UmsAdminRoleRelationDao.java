package com.macro.mall.dao;

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


    List<UmsRole> getRoleList(@Param("adminId") Long adminId);
}
