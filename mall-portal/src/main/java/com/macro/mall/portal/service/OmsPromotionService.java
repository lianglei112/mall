package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartPromotionItem;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/4 22:04
 * @deprecated 促销管理Service类
 */
public interface OmsPromotionService {

    /**
     * 计算购物车中的促销活动信息
     *
     * @param cartItemList 购物车
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);

}
