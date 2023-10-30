package com.macro.mall.portal.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/18 21:31
 * @deprecated 首页秒杀场次信息封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HomeFlashPromotion {

    /**
     * 本场开始时间
     */
    private Date startTime;

    /**
     * 本场结束时间
     */
    private Date endTime;

    /**
     * 下场开始时间
     */
    private Date nextStartTime;

    /**
     * 下场结束时间
     */
    private Date nextEndTime;

    /**
     * 属于该秒杀活动的商品
     */
    private List<FlashPromotionProduct> productList;

}
