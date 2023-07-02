package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.service.OmsCartItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/29 21:17
 * @deprecated 购物车管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
@Tag(name = "OmsCartItemController", description = "购物车管理控制器")
@Api(tags = "OmsCartItemController", description = "购物车管理控制器")
public class OmsCartItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemController.class);

    @Autowired
    private OmsCartItemService omsCartItemService;

    /**
     * 添加商品到购物车
     *
     * @param omsCartItem
     * @return
     */
    @ApiOperation(value = "添加商品到购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OmsCartItem omsCartItem) {
        int count = omsCartItemService.add(omsCartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加购物车失败，请稍后重试！");
    }


}
