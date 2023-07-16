package com.macro.mall.service.impl;

import com.macro.mall.mapper.SmsCouponMapper;
import com.macro.mall.service.SmsCouponService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:44
 * @deprecated 优惠券管理Service层实现类
 */
@Slf4j
@Service
public class SmsCouponServiceImpl implements SmsCouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCouponServiceImpl.class);

    @Autowired
    private SmsCouponMapper smsCouponMapper;


}
