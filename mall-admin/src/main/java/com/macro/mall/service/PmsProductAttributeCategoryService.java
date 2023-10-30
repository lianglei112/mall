package com.macro.mall.service;

import com.macro.mall.dto.PmsProductAttributeCategoryItem;
import com.macro.mall.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 22:49
 * @deprecated 商品属性分类service层
 */
public interface PmsProductAttributeCategoryService {


    int create(String name);

    int update(Long id, String name);

    int delete(Long id);

    PmsProductAttributeCategory getItem(Long id);

    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
