package com.macro.mall.portal.dao;

import com.macro.mall.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/9 16:59
 * @deprecated 订单商品信息管理自定义Dao
 */
@Repository
public interface OmsOrderItemDao {

    /**
     * 批量插入数据
     *
     * @param orderItemList
     * @return
     */
    int insertList(@Param("list") List<OmsOrderItem> orderItemList);

}
