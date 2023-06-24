package com.macro.mall.dto;

import com.macro.mall.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 15:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class PmsProductParam extends PmsProduct {

    @ApiModelProperty(value = "商品阶梯价格设置")
    private List<PmsProductLadder> pmsProductLadderList;

    @ApiModelProperty(value = "商品满减价格设置")
    private List<PmsProductFullReduction> pmsProductFullReductionList;

    @ApiModelProperty(value = "商品会员价格设置")
    private List<PmsMemberPrice> pmsMemberPriceList;

    @ApiModelProperty(value = "商品的sku库存设置")
    private List<PmsSkuStock> pmsSkuStockList;

    @ApiModelProperty(value = "商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> pmsProductAttributeValueList;

    @ApiModelProperty(value = "专题和商品相关联关系")
    private List<CmsSubjectProductRelation> cmsSubjectProductRelationList;

    @ApiModelProperty(value = "优选专区和商品的关系")
    private List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelationList;
}
