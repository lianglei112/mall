package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.UmsMemberCouponService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * @date 2023/7/17 22:28
 * @deprecated 用户优惠券管理Controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/member/coupon")
@Tag(name = "UmsMemberCouponController", description = "用户优惠券管理")
@Api(tags = "UmsMemberCouponController", description = "用户优惠券管理")
public class UmsMemberCouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberCouponController.class);

    @Autowired
    private UmsMemberCouponService umsMemberCouponService;

    @Autowired
    private OmsCartItemService omsCartItemService;

    @Autowired
    private UmsMemberService umsMemberService;

    /**
     * 领取指定优惠券
     *
     * @param couponId
     * @return
     */
    @ApiOperation("领取指定优惠券")
    @RequestMapping(value = "/add/{couponId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@PathVariable Long couponId) {
        umsMemberCouponService.add(couponId);
        return CommonResult.success(null, "领取成功！");
    }

    /**
     * 获取用户优惠券历史列表
     *
     * @param useStatus
     * @return
     */
    @ApiOperation("获取用户优惠券历史列表")
    @ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/listHistory", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<SmsCouponHistory> smsCouponHistoryList = umsMemberCouponService.listHistory(useStatus);
        return CommonResult.success(smsCouponHistoryList);
    }

    /**
     * 获取会员优惠券列表
     *
     * @param useStatus
     * @return
     */
    @ApiOperation("获取会员优惠券列表")
    @ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型: 0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCoupon>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<SmsCoupon> smsCouponList = umsMemberCouponService.list(useStatus);
        return CommonResult.success(smsCouponList);
    }

    /**
     * 获取当前商品相关优惠券
     *
     * @param productId
     * @return
     */
    @ApiOperation("获取当前商品相关优惠券")
    @RequestMapping(value = "/listByProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCoupon>> listByProduct(@PathVariable Long productId) {
        List<SmsCoupon> smsCouponList = umsMemberCouponService.listByProduct(productId);
        return CommonResult.success(smsCouponList);
    }

    /**
     * 获取登录会员购物车的相关优惠券
     *
     * @param type
     * @return
     */
    @ApiOperation("获取登录会员购物车的相关优惠券")
    @ApiImplicitParam(name = "type", value = "使用可用:0->不可用；1->可用",
            defaultValue = "1", allowableValues = "0,1", paramType = "path", dataType = "integer")
    @RequestMapping(value = "/list/cart/{type}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable Integer type) {
        List<CartPromotionItem> cartPromotionItemList = omsCartItemService.listPromotion(umsMemberService.getCurrentMember().getId(), null);
        List<SmsCouponHistoryDetail> couponHistoryList = umsMemberCouponService.listCart(cartPromotionItemList, type);
        return CommonResult.success(couponHistoryList);
    }
}
