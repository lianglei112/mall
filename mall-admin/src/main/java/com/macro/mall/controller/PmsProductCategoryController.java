package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductCategoryParam;
import com.macro.mall.model.PmsProductCategory;
import com.macro.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.swing.CachedPainter;

import javax.validation.Valid;
import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 13:25
 * @description 商品分类管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/productCategory")
@Tag(name = "PmsProductCategoryController", description = "商品分类管理控制器")
@Api(tags = "PmsProductCategoryController", description = "商品分类管理控制器")
public class PmsProductCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductCategoryController.class);

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    /**
     * 创建商品分类信息
     *
     * @param pmsProductCategoryParam
     * @return
     */
    @ApiOperation(value = "创建商品分类信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductCategoryParam pmsProductCategoryParam) {
        int count = pmsProductCategoryService.create(pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("创建商品分类信息失败，请稍后重试！");
    }

    /**
     * 分页查询商品分类信息
     *
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询商品分类信息")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<PmsProductCategory> pmsProductCategoryList = pmsProductCategoryService.getList(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsProductCategoryList));
    }

    /**
     * 根据id获取商品分类信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取商品分类")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
        PmsProductCategory pmsProductCategory = pmsProductCategoryService.getItem(id);
        return CommonResult.success(pmsProductCategory);
    }

    /**
     * 修改商品分类信息
     *
     * @param id
     * @param pmsProductCategoryParam
     * @return
     */
    @ApiOperation(value = "修改商品分类信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,
                               @Valid @RequestBody PmsProductCategoryParam pmsProductCategoryParam) {
        int count = pmsProductCategoryService.update(id, pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改商品分类信息失败，请稍后重试！");
    }

    /**
     * 删除商品分类信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除商品分类信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = pmsProductCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除商品分类信息失败，请稍后重试！");
    }


}
