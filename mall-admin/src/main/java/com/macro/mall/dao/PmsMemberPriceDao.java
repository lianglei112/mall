package com.macro.mall.dao;

import com.macro.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 会员价格操作持久层
 */
@Repository
public interface PmsMemberPriceDao {

    /**
     * 执行会员价格的批量插入
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<PmsMemberPrice> dataList);
}
