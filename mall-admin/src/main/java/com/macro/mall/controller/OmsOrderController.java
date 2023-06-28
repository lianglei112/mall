package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.*;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.service.OmsOrderService;
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
 * @date 2023/6/27 21:30
 * @deprecated 订单管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/order")
@Tag(name = "OmsOrderController", description = "订单管理控制器")
@Api(tags = "OmsOrderController", description = "订单管理控制器")
public class OmsOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderController.class);

    @Autowired
    private OmsOrderService omsOrderService;


    /**
     * 查询订单
     *
     * @param queryParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询订单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrder>> list(OmsOrderQueryParam queryParam,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<OmsOrder> omsOrderList = omsOrderService.list(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(omsOrderList));
    }

    /**
     * 批量发货
     *
     * @param deliveryParamList
     * @return
     */
    @ApiOperation(value = "批量发货")
    @RequestMapping(value = "/update/delivery", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
        int count = omsOrderService.delivery(deliveryParamList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量发货失败，请稍后重试！");
    }

    /**
     * 批量关闭订单
     *
     * @param ids
     * @param note
     * @return
     */
    @ApiOperation(value = "批量关闭订单")
    @RequestMapping(value = "/update/close", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult close(@RequestParam(value = "ids") List<Long> ids, @RequestParam(value = "note") String note) {
        int count = omsOrderService.close(ids, note);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量关闭订单失败，请稍后重试！");
    }


    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam(value = "ids") List<Long> ids) {
        int count = omsOrderService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除订单失败，请稍后重试！");
    }

    /**
     * 获取订单详情：订单信息、商品信息、操作记录
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取订单详情：订单信息、商品信息、操作记录")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderDetail> detail(@PathVariable Long id) {
        OmsOrderDetail omsOrderDetail = omsOrderService.detail(id);
        return CommonResult.success(omsOrderDetail);
    }

    /**
     * 修改收货人信息
     *
     * @param receiverInfoParam
     * @return
     */
    @ApiOperation(value = "修改收货人信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateReceiverInfo(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
        int count = omsOrderService.updateReceiverInfo(receiverInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改收货人信息失败，请稍后重试！");
    }

    /**
     * 修改订单费用信息
     *
     * @param omsMoneyInfoParam
     * @return
     */
    @ApiOperation(value = "修改订单费用信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateMoneyInfo(@RequestBody OmsMoneyInfoParam omsMoneyInfoParam) {
        int count = omsOrderService.updateMoneyInfo(omsMoneyInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改订单费用信息失败，请稍后重试！");
    }


    @ApiOperation(value = "备注订单")
    @RequestMapping(value = "/update/note", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNote(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "note") String note,
                                   @RequestParam(name = "status") Integer status) {
        int count = omsOrderService.updateNote(id, note, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("备注订单信息失败，请稍后重试！");
    }
}
