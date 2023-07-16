package com.macro.mall.service;

import com.macro.mall.model.SmsFlashPromotion;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 20:35
 * @desc 限时购活动管理Service层
 */
public interface SmsFlashPromotionService {

    SmsFlashPromotion getItem(Long id);

    List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize);

    int updateStatus(Long id, Integer status);

    int delete(Long id);

    int update(Long id, SmsFlashPromotion flashPromotion);

    int create(SmsFlashPromotion flashPromotion);

}
