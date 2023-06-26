package com.macro.mall.portal.domain;

import com.macro.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 18:00
 * @description 商品分类相关封装返回
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class PmsProductCategoryNode extends PmsProductCategory {

    @Setter
    @Getter
    @ApiModelProperty("子分类集合")
    List<PmsProductCategoryNode> children;

}
