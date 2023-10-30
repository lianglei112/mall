package com.macro.mall.dto;

import com.macro.mall.model.OmsCompanyAddress;
import com.macro.mall.model.OmsOrderReturnApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:38
 * @deprecated 获取订单退货详情实体类封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {

    @Getter
    @Setter
    @ApiModelProperty(value = "公司收货地址")
    private OmsCompanyAddress companyAddress;
}
