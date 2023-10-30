package com.macro.mall.service.impl;

import com.macro.mall.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.mapper.SmsFlashPromotionSessionMapper;
import com.macro.mall.model.SmsFlashPromotionSession;
import com.macro.mall.model.SmsFlashPromotionSessionExample;
import com.macro.mall.service.SmsFlashPromotionProductRelationService;
import com.macro.mall.service.SmsFlashPromotionSessionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 21:34
 * @deprecated 限时购场次管理 Service 层实现类
 */
@Slf4j
@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsFlashPromotionSessionServiceImpl.class);

    @Autowired
    private SmsFlashPromotionSessionMapper smsFlashPromotionSessionMapper;

    @Autowired
    private SmsFlashPromotionProductRelationService relationService;

    @Override
    public int create(SmsFlashPromotionSession promotionSession) {
        promotionSession.setCreateTime(new Date());
        return smsFlashPromotionSessionMapper.insert(promotionSession);
    }

    @Override
    public int update(Long id, SmsFlashPromotionSession promotionSession) {
        promotionSession.setId(id);
        return smsFlashPromotionSessionMapper.updateByPrimaryKeySelective(promotionSession);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession smsFlashPromotionSession = new SmsFlashPromotionSession();
        smsFlashPromotionSession.setId(id);
        smsFlashPromotionSession.setStatus(status);
        return smsFlashPromotionSessionMapper.updateByPrimaryKeySelective(smsFlashPromotionSession);
    }

    @Override
    public int delete(Long id) {
        return smsFlashPromotionSessionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsFlashPromotionSession getItem(Long id) {
        return smsFlashPromotionSessionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotionSession> list() {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        return smsFlashPromotionSessionMapper.selectByExample(example);
    }

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1);
        List<SmsFlashPromotionSession> list = smsFlashPromotionSessionMapper.selectByExample(example);
        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            long count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }
}
