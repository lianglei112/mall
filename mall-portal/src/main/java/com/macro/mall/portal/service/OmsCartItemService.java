package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/29 21:29
 * @deprecated 购物车管理service层
 */
public interface OmsCartItemService {

    int add(OmsCartItem omsCartItem);

    int delete(Long id, List<Long> ids);

    List<OmsCartItem> list(Long id);

    List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds);

    int updateQuantity(Long id, Long memberId, Integer quantity);

    CartProduct getCartProduct(Long productId);

    int updateAttr(OmsCartItem omsCartItem);

    int clear(Long memberId);

}
