package com.macro.mall.dto;

import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 21:08
 * @deprecated 查询订单详情实体类封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class OmsOrderDetail extends OmsOrder {
    @Setter
    @Getter
    @ApiModelProperty(value = "订单商品列表")
    List<OmsOrderItem> orderItemList;

    @Setter
    @Getter
    @ApiModelProperty(value = "订单操作记录列表")
    List<OmsOrderOperateHistory> historyList;
}
