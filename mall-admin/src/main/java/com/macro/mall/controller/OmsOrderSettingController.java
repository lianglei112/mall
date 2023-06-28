package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsOrderSetting;
import com.macro.mall.service.OmsOrderSettingService;
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
 * @date 2023/6/28 22:08
 * @deprecated 订单设置管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/orderSetting")
@Tag(name = "OmsOrderSettingController", description = "订单设置管理控制器")
@Api(tags = "OmsOrderSettingController", description = "订单设置管理控制器")
public class OmsOrderSettingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderSettingController.class);

    @Autowired
    private OmsOrderSettingService omsOrderSettingService;

    /**
     * 获取指定订单设置
     *
     * @param id
     * @return
     */
    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderSetting> getItem(@PathVariable Long id) {
        OmsOrderSetting setting = omsOrderSettingService.getItem(id);
        return CommonResult.success(setting);
    }

    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSetting omsOrderSetting) {
        int count = omsOrderSettingService.update(id, omsOrderSetting);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改指定订单设置失败，请稍后重试！");
    }
}
