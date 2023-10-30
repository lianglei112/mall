package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeAdvertise;
import com.macro.mall.service.SmsHomeAdvertiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:32
 * @deprecated
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/home/advertise")
@Tag(name = "SmsHomeAdvertiseController", description = "首页轮播广告管理")
@Api(tags = "SmsHomeAdvertiseController", description = "首页轮播广告管理")
public class SmsHomeAdvertiseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsHomeAdvertiseController.class);

    @Autowired
    private SmsHomeAdvertiseService smsHomeAdvertiseService;

    /**
     * 添加广告
     *
     * @param advertise
     * @return
     */
    @ApiOperation("添加广告")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsHomeAdvertise advertise) {
        int count = smsHomeAdvertiseService.create(advertise);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加广告失败，请稍后重试！");
    }

    /**
     * 删除广告
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeAdvertiseService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除广告失败，请稍后重试！");
    }

    /**
     * 修改上下线状态
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, Integer status) {
        int count = smsHomeAdvertiseService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改上下线状态失败，请稍后重试！");
    }

    /**
     * 获取广告详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取广告详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SmsHomeAdvertise> getItem(@PathVariable Long id) {
        SmsHomeAdvertise advertise = smsHomeAdvertiseService.getItem(id);
        return CommonResult.success(advertise);
    }

    /**
     * 修改广告
     *
     * @param id
     * @param advertise
     * @return
     */
    @ApiOperation("修改广告")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsHomeAdvertise advertise) {
        int count = smsHomeAdvertiseService.update(id, advertise);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改广告失败，请稍后重试！");
    }

    /**
     * 分页查询广告
     *
     * @param name
     * @param type
     * @param endTime
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询广告")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsHomeAdvertise>> list(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "type", required = false) Integer type,
                                                           @RequestParam(value = "endTime", required = false) String endTime,
                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsHomeAdvertise> advertiseList = smsHomeAdvertiseService.list(name, type, endTime, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(advertiseList));
    }
}
