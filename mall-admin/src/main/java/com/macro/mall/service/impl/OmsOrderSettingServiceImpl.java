package com.macro.mall.service.impl;

import com.macro.mall.mapper.OmsOrderSettingMapper;
import com.macro.mall.model.OmsOrderSetting;
import com.macro.mall.service.OmsOrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:10
 */
@Slf4j
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderSettingServiceImpl.class);

    @Autowired
    private OmsOrderSettingMapper omsOrderSettingMapper;

    @Override
    public OmsOrderSetting getItem(Long id) {
        return omsOrderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderSetting omsOrderSetting) {
        omsOrderSetting.setId(id);
        return omsOrderSettingMapper.updateByPrimaryKeySelective(omsOrderSetting);
    }


}
