package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.service.OmsPortalOrderService;
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
import java.util.Map;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/5 22:27
 * @deprecated 订单管理controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/order")
@Tag(name = "OmsPortalOrderController", description = "订单管理控制器")
@Api(tags = "OmsPortalOrderController", description = "订单管理控制器")
public class OmsPortalOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderController.class);

    @Autowired
    private OmsPortalOrderService omsPortalOrderService;

    /**
     * 根据购物车信息生成确认单
     *
     * @param cartIds
     * @return
     */
    @ApiOperation("根据购物车信息生成确认单")
    @RequestMapping(value = "/generateConfirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<ConfirmOrderResult> generateConfirmOrder(@RequestBody List<Long> cartIds) {
        ConfirmOrderResult confirmOrderResult = omsPortalOrderService.generateConfirmOrder(cartIds);
        return CommonResult.success(confirmOrderResult);
    }


    /**
     * 根据购物车信息生成订单
     *
     * @param orderParam
     * @return
     */
    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult generateOrder(@RequestBody OrderParam orderParam) {
        Map<String, Object> result = omsPortalOrderService.generateOrder(orderParam);
        return CommonResult.success(result, "下单成功！");
    }

    /**
     * 用户取消订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation("用户取消订单")
    @RequestMapping(value = "/cancelUserOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelUserOrder(Long orderId) {
        omsPortalOrderService.cancelOrder(orderId);
        return CommonResult.success(null);
    }

    /**
     * 用户支付成功的回调
     *
     * @param orderId
     * @param payType
     * @return
     */
    @ApiOperation("用户支付成功的回调")
    @RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult paySuccess(@RequestParam Long orderId, @RequestParam Integer payType) {
        Integer count = omsPortalOrderService.paySuccess(orderId, payType);
        return CommonResult.success(count, "支付成功！");
    }

    /**
     * 自动取消超时订单
     *
     * @return
     */
    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOutOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelTimeOutOrder() {
        omsPortalOrderService.cancelTimeOutOrder();
        return CommonResult.success(null);
    }

    /**
     * 取消单个超时订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation("取消单个超时订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelOrder(Long orderId) {
        omsPortalOrderService.sendDelayMessageCancelOrder(orderId);
        return CommonResult.success(null);
    }

    /**
     * 根据订单id获取订单详情
     *
     * @param orderId
     * @return
     */
    @ApiOperation("根据订单id获取订单详情")
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OmsOrderDetail> detail(@PathVariable Long orderId) {
        OmsOrderDetail orderDetail = omsPortalOrderService.detail(orderId);
        return CommonResult.success(orderDetail);
    }

    /**
     * 用户确认收货
     *
     * @param orderId
     * @return
     */
    @ApiOperation("用户确认收货")
    @RequestMapping(value = "/confirmReceiveOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult confirmReceiveOrder(@RequestParam Long orderId) {
        omsPortalOrderService.confirmReceiveOrder(orderId);
        return CommonResult.success(null);
    }

    /**
     * 用户删除订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation("用户删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteOrder(@RequestParam Long orderId) {
        omsPortalOrderService.deleteOrder(orderId);
        return CommonResult.success(null);
    }

    /**
     * 按照状态分页获取用户订单列表
     *
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("按状态分页获取用户订单列表")
    @ApiImplicitParam(name = "status", value = "订单状态：-1->全部；0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭",
            defaultValue = "-1", allowableValues = "-1,0,1,2,3,4", paramType = "query", dataType = "int")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrderDetail>> list(@RequestParam(name = "status") Integer status,
                                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        CommonPage<OmsOrderDetail> detailCommonPage = omsPortalOrderService.list(status, pageNum, pageSize);
        return CommonResult.success(detailCommonPage);
    }

}
