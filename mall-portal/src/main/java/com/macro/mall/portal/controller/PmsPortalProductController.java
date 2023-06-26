package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;
import com.macro.mall.portal.service.PmsPortalProductService;
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
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 15:06
 * @description 前台商品管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/brand")
@Tag(name = "PmsPortalProductController", description = "前台商品管理控制器")
@Api(tags = "PmsPortalProductController", description = "前台商品管理控制器")
public class PmsPortalProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsPortalProductController.class);

    @Autowired
    private PmsPortalProductService pmsPortalProductService;

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> search(@RequestParam(value = "keyword", required = false) String keyword,
                                                       @RequestParam(value = "brandId", required = false) Long brandId,
                                                       @RequestParam(value = "productCategoryId", required = false) Long productCategoryId,
                                                       @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                       @RequestParam(value = "sort", defaultValue = "0") Integer sort) {
        List<PmsProduct> pmsProductList = pmsPortalProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(pmsProductList));
    }

    /**
     * 以树形结构返回所有商品分类信息
     *
     * @return
     */
    @ApiOperation(value = "以树形结构返回所有商品分类信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsProductCategoryNode>> categoryTreeList() {
        List<PmsProductCategoryNode> list = pmsPortalProductService.categoryTreeList();
        return CommonResult.success(list);
    }

    /**
     * 获取前台商品详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取前台商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsPortalProductDetail> detail(@PathVariable Long id) {
        PmsPortalProductDetail pmsPortalProductDetail = pmsPortalProductService.detail(id);
        return CommonResult.success(pmsPortalProductDetail);
    }


}
