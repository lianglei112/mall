package com.macro.mall.service.impl;

import com.macro.mall.mapper.UmsResourceCategoryMapper;
import com.macro.mall.model.UmsResourceCategory;
import com.macro.mall.service.UmsResourceCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 14:08
 * @description 后台资源分类service实现层
 */
@Slf4j
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsResourceCategoryServiceImpl.class);

    @Autowired
    private UmsResourceCategoryMapper umsResourceCategoryMapper;

    @Override
    public int create(UmsResourceCategory umsResourceCategory) {
        return umsResourceCategoryMapper.insert(umsResourceCategory);
    }

    @Override
    public int delete(Long id) {
        return umsResourceCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        return umsResourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
    }


}
