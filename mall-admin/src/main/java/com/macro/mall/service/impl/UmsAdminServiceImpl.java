package com.macro.mall.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.UmsAdminRoleRelationDao;
import com.macro.mall.dto.UmsAdminParam;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsAdminExample;
import com.macro.mall.model.UmsResource;
import com.macro.mall.service.UmsAdminCacheService;
import com.macro.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 20:13
 * @deprecated service实现层
 */
@Slf4j
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    /**
     * 用户注册
     *
     * @param umsAdminParam
     * @return
     */
    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
        if (adminList.size() > 0) {
            return null;
        }
        //对密码进行加密操作
        String encode = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encode);
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    /**
     * 根据用户名或昵称进行查询
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<UmsAdmin> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        return umsAdminMapper.selectByExample(example);
    }

    /**
     * 获取指定用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UmsAdmin getItem(Long id) {
        return umsAdminMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新指定用户信息
     *
     * @param id
     * @param admin
     * @return
     */
    @Override
    public int update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin umsAdmin = umsAdminMapper.selectByPrimaryKey(id);
        if (umsAdmin.getPassword().equals(admin.getPassword())) {
            //与加密密码相同的不需要进行修改
            admin.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(admin.getPassword())) {
                admin.setPassword(null);
            } else {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        int count = umsAdminMapper.updateByPrimaryKey(admin);
        //TODO 删除缓存
        return count;
    }

    /**
     * 删除指定用户信息
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        return umsAdminMapper.deleteByPrimaryKey(id);
    }


    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = adminCacheService.getAdmin(username);
        if (admin != null) {
            return admin;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = umsAdminRoleRelationDao.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            adminCacheService.setResourceList(adminId, resourceList);
        }
        return resourceList;
    }


}
