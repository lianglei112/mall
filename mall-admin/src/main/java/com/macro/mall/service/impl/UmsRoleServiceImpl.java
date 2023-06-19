package com.macro.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.UmsAdminRoleRelationDao;
import com.macro.mall.mapper.UmsAdminRoleRelationMapper;
import com.macro.mall.mapper.UmsRoleMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.UmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 21:06
 */
@Slf4j
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return umsAdminRoleRelationDao.getRoleList(adminId);
    }

    /**
     * 给用户分配角色
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        umsAdminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //建立新的关系
        if (!CollectionUtil.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> umsAdminRoleRelations = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation adminRoleRelation = new UmsAdminRoleRelation();
                adminRoleRelation.setAdminId(adminId);
                adminRoleRelation.setRoleId(roleId);
                umsAdminRoleRelations.add(adminRoleRelation);
            }
            umsAdminRoleRelationDao.insertList(umsAdminRoleRelations);
        }
        // TODO 调用redis删除缓存
        return count;
    }

    @Override
    public List<UmsRole> listAll() {
        return umsRoleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public int create(UmsRole umsRole) {
        umsRole.setCreateTime(new Date());
        umsRole.setAdminCount(0);
        umsRole.setSort(0);
        return umsRoleMapper.insert(umsRole);
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return umsRoleMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsRole umsRole) {
        umsRole.setId(id);
        return umsRoleMapper.updateByPrimaryKeySelective(umsRole);
    }

    @Override
    public UmsRole getItem(Long id) {
        return umsRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        int count = umsRoleMapper.deleteByExample(example);
        //TODO 删除缓存
        return count;
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return null;
    }
}
