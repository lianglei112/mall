package com.macro.mall.portal.config.service;

import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsProduct;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 13:50
 * @description 前台品牌管理service层
 */
public interface PmsPortalBrandService {

    PmsBrand detail(Long brandId);

    List<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);
}
