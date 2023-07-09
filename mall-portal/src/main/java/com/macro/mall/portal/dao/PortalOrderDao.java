package com.macro.mall.portal.dao;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/9 21:30
 * @desc 前台订单管理自定义Dao
 */

import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.portal.domain.OmsOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortalOrderDao {

    int releaseSkuStockLock(@Param("itemList") List<OmsOrderItem> orderItemList);

    OmsOrderDetail getDetail(@Param("orderId") Long orderId);

    int updateSkuStock(@Param("itemList") List<OmsOrderItem> orderItemList);

}
