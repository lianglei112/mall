package com.macro.mall.portal.service;

import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/7 22:15
 * @deprecated 用户优惠券管理service层
 */
public interface UmsMemberCouponService {

    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type);

    void add(Long couponId);

    List<SmsCouponHistory> listHistory(Integer useStatus);

}
