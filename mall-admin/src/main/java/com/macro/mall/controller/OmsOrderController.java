package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.OmsOrderDeliveryParam;
import com.macro.mall.dto.OmsOrderQueryParam;
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

}
