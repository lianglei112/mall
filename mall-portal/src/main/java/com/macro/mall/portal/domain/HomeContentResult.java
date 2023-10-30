package com.macro.mall.portal.domain;

import com.macro.mall.model.*;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/18 21:25
 * @deprecated 首页内容返回信息封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HomeContentResult {

    /**
     * 轮播广告
     */
    private List<SmsHomeAdvertise> advertiseList;

    /**
     * 推荐品牌
     */
    private List<SmsHomeBrand> brandList;

    /**
     * 当前秒杀场次
     */
    private HomeFlashPromotion homeFlashPromotion;

    /**
     * 新品推荐
     */
    private List<SmsHomeNewProduct> newProductList;

    /**
     * 人气推荐
     */
    private List<PmsProduct> hotProductList;

    /**
     * 推荐专题
     */
    private List<CmsSubject> subjectList;

}
