package com.macro.mall.dao;

import com.macro.mall.dto.ProductAttrInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 23:14
 * @deprecated 商品属性相关操作持久层
 */
@Repository
public interface PmsProductAttributeDao {

    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

}
