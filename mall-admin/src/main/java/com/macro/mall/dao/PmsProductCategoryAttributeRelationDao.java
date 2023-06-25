package com.macro.mall.dao;

import com.macro.mall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 15:19
 * @description 商品属性相关操作持久层
 */
@Repository
public interface PmsProductCategoryAttributeRelationDao {


    /**
     * 执行商品属性批量插入操作
     *
     * @param relationList
     * @return
     */
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> relationList);
}
