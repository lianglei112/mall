package com.macro.mall.portal.domain;

import com.macro.mall.model.PmsProduct;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/18 21:33
 * @deprecated 秒杀信息和商品对象封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FlashPromotionProduct extends PmsProduct {

    /**
     * 秒杀价格
     */
    private BigDecimal flashPromotionPrice;

    /**
     * 用于秒杀到数量
     */
    private Integer flashPromotionCount;

    /**
     * 秒杀限购数量
     */
    private Integer flashPromotionLimit;
}
