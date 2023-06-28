package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.OmsOrderReturnApplyResult;
import com.macro.mall.dto.OmsReturnApplyQueryParam;
import com.macro.mall.dto.OmsUpdateStatusParam;
import com.macro.mall.model.OmsOrderReturnApply;
import com.macro.mall.service.OmsOrderReturnApplService;
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
 * @date 2023/6/28 22:21
 * @deprecated 订单退货申请管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/orderSetting")
@Tag(name = "OmsOrderReturnApplyController", description = "订单退货申请管理控制器")
@Api(tags = "OmsOrderReturnApplyController", description = "订单退货申请管理控制器")
public class OmsOrderReturnApplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderReturnApplyController.class);

    @Autowired
    private OmsOrderReturnApplService omsOrderReturnApplService;

    /**
     * 分页查询退货申请
     *
     * @param queryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询退货申请")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrderReturnApply>> list(@RequestBody OmsReturnApplyQueryParam queryParam,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<OmsOrderReturnApply> omsOrderReturnApplyList = omsOrderReturnApplService.list(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(omsOrderReturnApplyList));
    }

    /**
     * 批量删除退货申请
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除退货申请")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = omsOrderReturnApplService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取退货申请详情
     *
     * @param id
     * @return
     */
    @ApiOperation("获取退货申请详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderReturnApplyResult> getItem(@PathVariable Long id) {
        OmsOrderReturnApplyResult result = omsOrderReturnApplService.getItem(id);
        return CommonResult.success(result);
    }

    /**
     * 修改退货申请状态
     *
     * @param id
     * @param statusParam
     * @return
     */
    @ApiOperation("修改退货申请状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestBody OmsUpdateStatusParam statusParam) {
        int count = omsOrderReturnApplService.updateStatus(id, statusParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改退货申请状态，请稍后重试！");
    }
}
