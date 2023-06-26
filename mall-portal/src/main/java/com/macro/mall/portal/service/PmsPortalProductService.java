package com.macro.mall.portal.service;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 15:09
 * @description 前台商品管理service层
 */
public interface PmsPortalProductService {

    List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    List<PmsProductCategoryNode> categoryTreeList();

    PmsPortalProductDetail detail(Long id);
}
