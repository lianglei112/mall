package com.macro.mall.portal.service;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.domain.OrderParam;

import java.util.List;
import java.util.Map;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/5 22:29
 * @deprecated 订单管理service层
 */
public interface OmsPortalOrderService {

    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);

    Map<String, Object> generateOrder(OrderParam orderParam);

    void cancelOrder(Long orderId);

    Integer paySuccess(Long orderId, Integer payType);

    Integer cancelTimeOutOrder();

    /**
     * 发送延迟消息取消订单
     *
     * @param orderId
     */
    void sendDelayMessageCancelOrder(Long orderId);

    OmsOrderDetail detail(Long orderId);

    void confirmReceiveOrder(Long orderId);

    void deleteOrder(Long orderId);

    CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize);

}
