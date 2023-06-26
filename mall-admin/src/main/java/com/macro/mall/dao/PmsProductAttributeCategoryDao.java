package com.macro.mall.dao;

import com.macro.mall.dto.PmsProductAttributeCategoryItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 22:56
 * @deprecated 商品属性相关操作持久层
 */
@Repository
public interface PmsProductAttributeCategoryDao {

    /**
     * 获取包含属性的商品属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();

}
