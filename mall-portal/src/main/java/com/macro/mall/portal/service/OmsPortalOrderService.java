package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.ConfirmOrderResult;
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
}
