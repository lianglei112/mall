package com.macro.mall.service;

import com.macro.mall.model.OmsOrderSetting;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:10
 * @deprecated 订单设置管理service层
 */
public interface OmsOrderSettingService {

    OmsOrderSetting getItem(Long id);

    int update(Long id, OmsOrderSetting omsOrderSetting);

}
