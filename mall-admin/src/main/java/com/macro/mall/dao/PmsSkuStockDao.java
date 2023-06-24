package com.macro.mall.dao;

import com.macro.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 商品库存相关操作持久层
 */
@Repository
public interface PmsSkuStockDao {

    /**
     * 商品库存相关数据批量插入
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<PmsSkuStock> dataList);
}
