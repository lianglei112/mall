package com.macro.mall.service;

import com.macro.mall.dto.PmsProductAttributeParam;
import com.macro.mall.dto.ProductAttrInfo;
import com.macro.mall.model.PmsProductAttribute;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 23:03
 * @deprecated 商品属性管理service层
 */
public interface PmsProductAttributeService {

    List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    int create(PmsProductAttributeParam productAttributeParam);

    int update(Long id, PmsProductAttributeParam productAttributeParam);

    PmsProductAttribute getItem(Long id);

    int delete(List<Long> ids);

    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
