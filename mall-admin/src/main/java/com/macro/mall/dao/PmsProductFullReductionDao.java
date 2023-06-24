package com.macro.mall.dao;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 满减价格操作持久层
 */
@Repository
public interface PmsProductFullReductionDao {

    /**
     * 执行满减价格批量插入操作
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<PmsProductFullReduction> dataList);

}
