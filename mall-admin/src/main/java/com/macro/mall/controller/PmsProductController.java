package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 15:38
 * @deprecated 商品管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/product")
@Tag(name = "PmsProductController", description = "商品管理控制器")
@Api(tags = "PmsProductController", description = "商品管理控制器")
public class PmsProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductController.class);

    @Autowired
    private PmsProductService pmsProductService;

    /**
     * 创建商品
     *
     * @param pmsProductParam
     * @return
     */
    @ApiOperation(value = "创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@Valid @RequestBody PmsProductParam pmsProductParam) {
        int count = pmsProductService.create(pmsProductParam);
        if (count > 0) {
            return CommonResult.success("商品创建成功！");
        }
        return CommonResult.failed("商品创建失败，请稍后重试！");
    }

    /**
     * 查询商品
     *
     * @param productQueryParam
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation(value = "查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> getList(@RequestBody PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> pmsProductList = pmsProductService.getList(productQueryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsProductList));
    }

    /**
     * 根据商品id获取商品编辑信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long id) {
        PmsProductResult pmsProductResult = pmsProductService.getUpdateInfo(id);
        return CommonResult.success(pmsProductResult);
    }

    /**
     * 更新商品
     *
     * @param id
     * @param pmsProductParam
     * @return
     */
    @ApiOperation(value = "更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam pmsProductParam) {
        int count = pmsProductService.update(id, pmsProductParam);
        if (count > 0) {
            return CommonResult.success("商品更新成功！");
        }
        return CommonResult.failed("商品更新失败，请稍后重试！");
    }

    /**
     * 根据商品名称或货号模糊查询
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<PmsProduct>> getList(@RequestParam(value = "keyword") String keyword) {
        List<PmsProduct> pmsProductList = pmsProductService.list(keyword);
        return CommonResult.success(pmsProductList);
    }

    /**
     * 批量上下架
     *
     * @param ids
     * @param publishStatus
     * @return
     */
    @ApiOperation(value = "批量上下架")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam(value = "ids") List<Long> ids,
                                            @RequestParam(value = "publishStatus") Integer publishStatus) {
        int count = pmsProductService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量上下架失败！");
    }

    /**
     * 批量推荐商品
     *
     * @param ids
     * @param recommendStatus
     * @return
     */
    @ApiOperation(value = "批量推荐商品")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam(value = "ids") List<Long> ids,
                                              @RequestParam(value = "recommendStatus") Integer recommendStatus) {
        int count = pmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量设置新品失败，请稍后重试！");
    }

    /**
     * 批量设置为新品
     *
     * @param ids
     * @param newStatus
     * @return
     */
    @ApiOperation(value = "批量设置为新品")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNewStatus(@RequestParam(value = "ids") List<Long> ids,
                                        @RequestParam(value = "newStatus") Integer newStatus) {
        int count = pmsProductService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量设置新品失败，请稍后重试！");
    }

    /**
     * 批量修改删除状态
     *
     * @param ids
     * @param deleteStatus
     * @return
     */
    @ApiOperation(value = "批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDeleteStatus(@RequestParam(value = "ids") List<Long> ids,
                                           @RequestParam(value = "deleteStatus") Integer deleteStatus) {
        int count = pmsProductService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改删除状态失败，请稍后重试！");
    }

    /**
     * 批量修改审核状态
     *
     * @param ids
     * @param verifyStatus
     * @param detail
     * @return
     */
    @ApiOperation(value = "批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateVerifyStatus(@RequestParam(value = "ids") List<Long> ids,
                                           @RequestParam(value = "verifyStatus") Integer verifyStatus,
                                           @RequestParam(value = "detail") String detail) {
        int count = pmsProductService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改审核状态失败，请稍后重试！");
    }
}
