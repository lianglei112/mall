package com.macro.mall.service;

import com.macro.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 23:12
 * @deprecated 订单退货原因管理service层
 */
public interface OmsOrderReturnReasonService {

    int create(OmsOrderReturnReason returnReason);

    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

    OmsOrderReturnReason getItem(Long id);

    int update(Long id, OmsOrderReturnReason returnReason);

    int delete(List<Long> ids);

    int updateStatus(List<Long> ids, Integer status);
}
