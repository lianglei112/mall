package com.macro.mall.dao;

import com.macro.mall.dto.OmsOrderDeliveryParam;
import com.macro.mall.dto.OmsOrderDetail;
import com.macro.mall.dto.OmsOrderQueryParam;
import com.macro.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/27 21:53
 * @deprecated 订单相关操作持久层
 */
@Repository
public interface OmsOrderDao {


    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);


    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);


    OmsOrderDetail detail(@Param("id") Long id);
}
