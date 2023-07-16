package com.macro.mall.controller;

import com.macro.mall.service.SmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:42
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/coupon")
@Tag(name = "SmsCouponController", description = "优惠券管理")
@Api(tags = "SmsCouponController", description = "优惠券管理")
public class SmsCouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCouponController.class);

    @Autowired
    private SmsCouponService smsCouponService;


}
