package com.macro.mall.service;

import com.macro.mall.dto.SmsCouponParam;
import com.macro.mall.model.SmsCoupon;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:43
 * @deprecated 优惠券管理Service 层
 */
public interface SmsCouponService {

    int create(SmsCouponParam smsCouponParam);

    int delete(Long id);

    SmsCouponParam getItem(Long id);

    int update(Long id, SmsCouponParam couponParam);

    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

}
