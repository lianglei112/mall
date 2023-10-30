package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsFlashPromotion;
import com.macro.mall.service.SmsFlashPromotionService;
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
 * @date 2023/7/16 20:31
 * @desc 限时购活动管理Controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/flash")
@Tag(name = "SmsFlashPromotionController", description = "限时购活动管理")
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
public class SmsFlashPromotionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsFlashPromotionController.class);

    @Autowired
    private SmsFlashPromotionService smsFlashPromotionService;

    /**
     * 获取活动详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取活动详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    private CommonResult getItem(@PathVariable Long id) {
        SmsFlashPromotion flashPromotion = smsFlashPromotionService.getItem(id);
        return CommonResult.success(flashPromotion);
    }

    /**
     * 根据活动名称分页查询数据
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsFlashPromotion>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsFlashPromotion> smsFlashPromotionList = smsFlashPromotionService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(smsFlashPromotionList));
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
    public CommonResult update(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        int count = smsFlashPromotionService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新上下线状态失败，请稍后重试！");
    }

    /**
     * 删除活动
     *
     * @param id
     * @return
     */
    @ApiOperation("删除活动")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = smsFlashPromotionService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除失败，请稍后重试！");
    }

    /**
     * 编辑活动
     *
     * @param id
     * @param flashPromotion
     * @return
     */
    @ApiOperation("编辑活动")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
        int count = smsFlashPromotionService.update(id, flashPromotion);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("编辑信息失败，请稍后重试！");
    }

    /**
     * 添加活动
     *
     * @param flashPromotion
     * @return
     */
    @ApiOperation("添加活动")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsFlashPromotion flashPromotion) {
        int count = smsFlashPromotionService.create(flashPromotion);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加信息失败，请稍后重试！");
    }
}
