package com.macro.mall.portal.config.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.config.service.PmsPortalBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 13:32
 * @description 前台品牌管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/brand")
@Tag(name = "PmsPortalBrandController", description = "前台品牌管理控制器")
@Api(tags = "PmsPortalBrandController", description = "前台品牌管理控制器")
public class PmsPortalBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsPortalBrandController.class);

    @Autowired
    private PmsPortalBrandService pmsPortalBrandService;

    /**
     * 获取商品详情
     *
     * @param brandId
     * @return
     */
    @ApiOperation(value = "获取商品详情")
    @RequestMapping(value = "/detail/{brandId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> detail(@PathVariable Long brandId) {
        PmsBrand pmsBrand = pmsPortalBrandService.detail(brandId);
        return CommonResult.success(pmsBrand);
    }

    /**
     * 分页获取品牌相关商品
     *
     * @param brandId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页获取品牌相关商品")
    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> productList(@RequestParam(value = "brandId") Long brandId,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<PmsProduct> pmsProductList = pmsPortalBrandService.productList(brandId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsProductList));
    }

    //TODO 缺少分页推荐商品的接口

}
