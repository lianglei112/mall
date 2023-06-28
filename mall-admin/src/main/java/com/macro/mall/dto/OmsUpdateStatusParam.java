package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:47
 * @deprecated 退货申请原因实体类入参封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class OmsUpdateStatusParam {

    @ApiModelProperty("服务单号")
    private Long id;

    @ApiModelProperty("收货地址关联id")
    private Long companyAddressId;

    @ApiModelProperty("确认退款金额")
    private BigDecimal returnAmount;

    @ApiModelProperty("处理备注")
    private String handleNote;

    @ApiModelProperty("处理人")
    private String handleMan;

    @ApiModelProperty("收货备注")
    private String receiveNote;

    @ApiModelProperty("收货人")
    private String receiveMan;

    @ApiModelProperty("申请状态：1->退货中；2->已完成；3->已拒绝")
    private Integer status;
}
