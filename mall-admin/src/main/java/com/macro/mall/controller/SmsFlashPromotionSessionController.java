package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.model.SmsFlashPromotionSession;
import com.macro.mall.service.SmsFlashPromotionSessionService;
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
 * @date 2023/7/16 21:29
 * @deprecated 限时购场次管理 Controller 控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/flashSession")
@Tag(name = "SmsFlashPromotionSessionController", description = "限时购场次管理")
@Api(tags = "SmsFlashPromotionSessionController", description = "限时购场次管理")
public class SmsFlashPromotionSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsFlashPromotionSessionController.class);

    @Autowired
    private SmsFlashPromotionSessionService smsFlashPromotionSessionService;

    /**
     * 添加场次
     *
     * @param promotionSession
     * @return
     */
    @ApiOperation("添加场次")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsFlashPromotionSession promotionSession) {
        int count = smsFlashPromotionSessionService.create(promotionSession);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加失败，请稍后重试！");
    }

    /**
     * 修改场次
     *
     * @param id
     * @param promotionSession
     * @return
     */
    @ApiOperation("修改场次")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotionSession promotionSession) {
        int count = smsFlashPromotionSessionService.update(id, promotionSession);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改失败，请稍后重试！");
    }

    /**
     * 修改启用状态
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("修改启用状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, Integer status) {
        int count = smsFlashPromotionSessionService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改启用状态失败，请稍后重试！");
    }

    /**
     * 删除场次
     *
     * @param id
     * @return
     */
    @ApiOperation("删除场次")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = smsFlashPromotionSessionService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除场次失败，请稍后重试！");
    }

    /**
     * 获取场次详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取场次详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SmsFlashPromotionSession> getItem(@PathVariable Long id) {
        SmsFlashPromotionSession promotionSession = smsFlashPromotionSessionService.getItem(id);
        return CommonResult.success(promotionSession);
    }

    /**
     * 获取全部场次
     *
     * @return
     */
    @ApiOperation("获取全部场次")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsFlashPromotionSession>> list() {
        List<SmsFlashPromotionSession> promotionSessionList = smsFlashPromotionSessionService.list();
        return CommonResult.success(promotionSessionList);
    }

    /**
     * 获取全部可选场次及其数量
     *
     * @param flashPromotionId
     * @return
     */
    @ApiOperation("获取全部可选场次及其数量")
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsFlashPromotionSessionDetail>> selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> promotionSessionList = smsFlashPromotionSessionService.selectList(flashPromotionId);
        return CommonResult.success(promotionSessionList);
    }
}
