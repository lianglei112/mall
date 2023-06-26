package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 20:35
 * @deprecated 商品查询参数
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class PmsProductQueryParam {

    @ApiModelProperty(value = "商品名称模糊查询关键字")
    private String keyword;

    @ApiModelProperty(value = "商品货号")
    private String productSn;

    @ApiModelProperty(value = "商品分类编号")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品品牌编号")
    private Long brandId;

    @ApiModelProperty(value = "上下架状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "审核状态")
    private Integer verifyStatus;

}
