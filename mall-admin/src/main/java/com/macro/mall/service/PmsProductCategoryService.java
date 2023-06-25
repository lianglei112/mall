package com.macro.mall.service;

import com.macro.mall.dto.PmsProductCategoryParam;
import com.macro.mall.model.PmsProductCategory;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 13:45
 * @description 商品分类service层
 */
public interface PmsProductCategoryService {

    List<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize);

    int create(PmsProductCategoryParam pmsProductCategoryParam);

    PmsProductCategory getItem(Long id);

    int update(Long id, PmsProductCategoryParam pmsProductCategoryParam);

    int delete(Long id);
}
