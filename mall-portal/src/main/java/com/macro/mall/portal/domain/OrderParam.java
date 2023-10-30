package com.macro.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/8 19:55
 * @deprecated 生成订单相关参数实体类封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class OrderParam {

    /**
     * 收货地址ID
     */
    @ApiModelProperty("收货地址ID")
    private Long memberReceiveAddressId;

    /**
     * 优惠券ID
     */
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    /**
     * 使用的积分数
     */
    @ApiModelProperty("使用的积分数")
    private Integer useIntegration;

    /**
     * 支付方式
     */
    @ApiModelProperty("支付方式")
    private Integer payType;

    /**
     * 被选中的购物车商品ID
     */
    @ApiModelProperty("被选中的购物车商品ID")
    private List<Long> cartIds;

}
