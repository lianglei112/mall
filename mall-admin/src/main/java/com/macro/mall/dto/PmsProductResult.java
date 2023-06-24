package com.macro.mall.dto;

import com.macro.mall.model.PmsProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 21:03
 * @deprecated 查询单个商品修改之后返回的结果
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PmsProductResult extends PmsProductParam {

    @Getter
    @Setter
    @ApiModelProperty(value = "商品所选分类的父id")
    private Long cateParentId;

}
