package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.SmsCouponParam;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.service.SmsCouponService;
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

    /**
     * 添加优惠券
     *
     * @param smsCouponParam
     * @return
     */
    @ApiOperation("添加优惠券")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody SmsCouponParam smsCouponParam) {
        int count = smsCouponService.create(smsCouponParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加优惠券信息失败，请稍后重试！");
    }

    /**
     * 刪除优惠券
     *
     * @param id
     * @return
     */
    @ApiOperation("删除优惠券")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = smsCouponService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除优惠券信息失败，请稍后重试！");
    }

    /**
     * 获取单个优惠券的详细信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个优惠券的详细信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SmsCouponParam> getItem(@PathVariable Long id) {
        SmsCouponParam couponParam = smsCouponService.getItem(id);
        return CommonResult.success(couponParam);
    }

    /**
     * 修改优惠券
     *
     * @param id
     * @param couponParam
     * @return
     */
    @ApiOperation("修改优惠券")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsCouponParam couponParam) {
        int count = smsCouponService.update(id, couponParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改优惠券信息失败，请稍后重试！");
    }

    /**
     * 根据优惠券名称和类型分页获取优惠券列表
     *
     * @param name
     * @param type
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("根据优惠券名称和类型分页获取优惠券列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsCoupon>> list(@RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "type", required = false) Integer type,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsCoupon> couponList = smsCouponService.list(name, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(couponList));
    }
}