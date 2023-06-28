package com.macro.mall.service;

import com.macro.mall.dto.OmsOrderReturnApplyResult;
import com.macro.mall.dto.OmsReturnApplyQueryParam;
import com.macro.mall.dto.OmsUpdateStatusParam;
import com.macro.mall.model.OmsOrderReturnApply;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:23
 * @deprecated 订单退货申请管理Service层
 */
public interface OmsOrderReturnApplService {

    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageNum, Integer pageSize);

    int delete(List<Long> ids);

    OmsOrderReturnApplyResult getItem(Long id);

    int updateStatus(Long id, OmsUpdateStatusParam statusParam);
}
