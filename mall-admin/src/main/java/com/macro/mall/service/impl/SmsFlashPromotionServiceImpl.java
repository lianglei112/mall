package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.SmsFlashPromotionMapper;
import com.macro.mall.model.SmsFlashPromotion;
import com.macro.mall.model.SmsFlashPromotionExample;
import com.macro.mall.service.SmsFlashPromotionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 20:41
 * @desc 限时购活动管理Service层实现类
 */
@Slf4j
@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsFlashPromotionServiceImpl.class);

    @Autowired
    private SmsFlashPromotionMapper smsFlashPromotionMapper;


    @Override
    public SmsFlashPromotion getItem(Long id) {
        return smsFlashPromotionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        if (StringUtils.isNotEmpty(keyword)) {
            example.createCriteria().andTitleLike("%" + keyword + "%");
        }
        return smsFlashPromotionMapper.selectByExample(example);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotion smsFlashPromotion = new SmsFlashPromotion();
        smsFlashPromotion.setStatus(status);
        smsFlashPromotion.setId(id);
        return smsFlashPromotionMapper.updateByPrimaryKeySelective(smsFlashPromotion);
    }

    @Override
    public int delete(Long id) {
        return smsFlashPromotionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Long id, SmsFlashPromotion flashPromotion) {
        SmsFlashPromotionExample example = new SmsFlashPromotionExample();
        example.createCriteria().andIdEqualTo(id);
        return smsFlashPromotionMapper.updateByExampleSelective(flashPromotion, example);
    }

    @Override
    public int create(SmsFlashPromotion flashPromotion) {
        flashPromotion.setCreateTime(new Date());
        return smsFlashPromotionMapper.insert(flashPromotion);
    }
}
