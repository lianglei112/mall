package com.macro.mall.service;

import com.macro.mall.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.model.SmsFlashPromotionSession;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 21:33
 * @deprecated 限时购场次管理 Service 层
 */
public interface SmsFlashPromotionSessionService {

    int create(SmsFlashPromotionSession promotionSession);

    int update(Long id, SmsFlashPromotionSession promotionSession);

    int updateStatus(Long id, Integer status);

    int delete(Long id);

    SmsFlashPromotionSession getItem(Long id);

    List<SmsFlashPromotionSession> list();

    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);

}
