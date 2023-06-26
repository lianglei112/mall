package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsSkuStock;
import com.macro.mall.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
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
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 11:15
 * @description sku商品库存管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sku")
@Tag(name = "PmsSkuStockController", description = "sku商品库存管理控制器")
@Api(tags = "PmsSkuStockController", description = "sku商品库存管理控制器")
public class PmsSkuStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsSkuStockController.class);

    @Autowired
    private PmsSkuStockService pmsSkuStockService;

    /**
     * 根据商品id及sku编码模糊搜索sku库存
     *
     * @param pid
     * @param keyword
     * @return
     */
    @ApiOperation(value = "根据商品id及sku编码模糊搜索sku库存")
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid,
                                                   @RequestParam(value = "keyword") String keyword) {
        List<PmsSkuStock> pmsSkuStockList = pmsSkuStockService.getList(pid, keyword);
        return CommonResult.success(pmsSkuStockList);
    }

    /**
     * 批量更新sku库存信息
     *
     * @param pid
     * @param stockList
     * @return
     */
    @ApiOperation(value = "批量更新sku库存信息")
    @RequestMapping(value = "/update/{pid}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long pid,
                               @RequestBody List<PmsSkuStock> stockList) {
        int count = pmsSkuStockService.update(pid, stockList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量更新失败，请稍后重试！");
    }
}
