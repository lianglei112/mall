package com.macro.mall.portal.dao;

import com.macro.mall.portal.domain.CartProduct;
import org.apache.ibatis.annotations.Param;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/3 22:15
 * @deprecated 前台购物车商品管理自定义Dao
 */
public interface PortalProductDao {

    CartProduct getCartProduct(@Param("productId") Long productId);

}
