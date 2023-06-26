package com.macro.mall.dto;

import com.macro.mall.model.PmsProductAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/26 22:53
 * @deprecated 商品属性实体类封装
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class PmsProductAttributeCategoryItem {

    @Getter
    @Setter
    @ApiModelProperty(value = "商品属性列表")
    private List<PmsProductAttribute> productAttributeList;
}
