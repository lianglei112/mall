package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 21:55
 * @deprecated 修改费用信息入参实体类封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class OmsMoneyInfoParam {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;

    @ApiModelProperty("管理员后台调整订单所使用的折扣金额")
    private BigDecimal discountAmount;

    @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;
}
