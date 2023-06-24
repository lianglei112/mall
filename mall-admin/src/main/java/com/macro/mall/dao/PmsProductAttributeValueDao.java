package com.macro.mall.dao;


import com.macro.mall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 商品属性操作持久层
 */
@Repository
public interface PmsProductAttributeValueDao {


    /**
     * 执行商品属性批量插入操作
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<PmsProductAttributeValue> dataList);
}
