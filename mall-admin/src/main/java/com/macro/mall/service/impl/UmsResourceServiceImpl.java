package com.macro.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.UmsResourceMapper;
import com.macro.mall.model.UmsResource;
import com.macro.mall.model.UmsResourceExample;
import com.macro.mall.service.UmsResourceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 11:09
 * @description 资源管理service实现层
 */
@Slf4j
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsResourceServiceImpl.class);

    @Autowired
    private UmsResourceMapper umsResourceMapper;

    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return umsResourceMapper.insert(umsResource);
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        int count = umsResourceMapper.updateByPrimaryKeySelective(umsResource);
        return count;
    }

    @Override
    public int delete(Long id) {
        //TODO 删除缓存
        return umsResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UmsResource getItem(Long id) {
        return umsResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsResource> listAll() {
        return umsResourceMapper.selectByExample(new UmsResourceExample());
    }

    @Override
    public List<UmsResource> list(Long categoryId, String nameKeyWord, String urlKeyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsResourceExample example = new UmsResourceExample();
        UmsResourceExample.Criteria criteria = example.createCriteria();
        if (categoryId != null) {
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (StrUtil.isNotBlank(nameKeyWord)) {
            criteria.andNameLike("%" + nameKeyWord + "%");
        }
        if (StrUtil.isNotBlank(urlKeyword)) {
            criteria.andUrlLike("%" + urlKeyword + "%");
        }
        return umsResourceMapper.selectByExample(example);
    }


}
