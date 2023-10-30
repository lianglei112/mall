package com.macro.mall.service;

import com.macro.mall.dto.*;
import com.macro.mall.model.OmsOrder;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/27 21:32
 * @deprecated 订单管理service层
 */
public interface OmsOrderService {

    List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageNum, Integer pageSize);

    int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

    int close(List<Long> ids, String note);

    int delete(List<Long> ids);

    OmsOrderDetail detail(Long id);

    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    int updateMoneyInfo(OmsMoneyInfoParam omsMoneyInfoParam);

    int updateNote(Long id, String note, Integer status);

}
