package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 23:10
 * @deprecated 商品分类对应属性信息实体类封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class ProductAttrInfo {

    @ApiModelProperty("商品属性ID")
    private Long attributeId;

    @ApiModelProperty("商品属性分类ID")
    private Long attributeCategoryId;
}
