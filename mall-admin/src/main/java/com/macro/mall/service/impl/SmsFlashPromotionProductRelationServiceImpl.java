package com.macro.mall.service.impl;

import com.macro.mall.mapper.SmsFlashPromotionProductRelationMapper;
import com.macro.mall.model.SmsFlashPromotionProductRelationExample;
import com.macro.mall.service.SmsFlashPromotionProductRelationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 21:48
 * @deprecated 限时购商品关联管理Service 实现类
 */
@Slf4j
@Service
public class SmsFlashPromotionProductRelationServiceImpl implements SmsFlashPromotionProductRelationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsFlashPromotionProductRelationServiceImpl.class);

    @Autowired
    private SmsFlashPromotionProductRelationMapper relationMapper;

    @Override
    public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
        SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
        example.createCriteria()
                .andFlashPromotionIdEqualTo(flashPromotionId)
                .andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
        return relationMapper.countByExample(example);
    }
}
