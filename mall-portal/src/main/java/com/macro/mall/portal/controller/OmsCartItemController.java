package com.macro.mall.portal.controller;

import com.macro.mall.portal.service.OmsCartItemService;
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
 * @date 2023/6/29 21:17
 * @deprecated 购物车管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/returnReason")
@Tag(name = "OmsOrderReturnReasonController", description = "购物车管理控制器")
@Api(tags = "OmsOrderReturnReasonController", description = "购物车管理控制器")
public class OmsCartItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemController.class);

    @Autowired
    private OmsCartItemService omsCartItemService;


}
