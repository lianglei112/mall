package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsResourceCategory;
import com.macro.mall.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 13:59
 * @description 资源分类管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/resourceCategory")
@Tag(name = "UmsResourceCategoryController", description = "后台资源管理控制器")
@Api(tags = "UmsResourceCategoryController", description = "后台资源管理控制器")
public class UmsResourceCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsResourceCategoryController.class);

    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;


    /**
     * 添加后台资源分类
     *
     * @param umsResourceCategory
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "添加后台资源分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResourceCategory umsResourceCategory) {
        int count = umsResourceCategoryService.create(umsResourceCategory);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新增后台资源分类失败，请稍后重试！");
    }

    /**
     * 根据ID修改后台资源
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据ID修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable @NotNull(message = "资源分类id不能为空！") Long id,
                               @RequestBody UmsResourceCategory umsResourceCategory) {
        int count = umsResourceCategoryService.update(id, umsResourceCategory);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改失败，请稍后重试！");
    }

    /**
     * 根据ID删除后台资源
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable @NotNull(message = "资源分类id不能为空！") Long id) {
        int count = umsResourceCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除失败，请稍后重试！");
    }

    /**
     * 查询所有后台资源
     *
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> umsResourceCategoryList = umsResourceCategoryService.listAll();
        return CommonResult.success(umsResourceCategoryList);
    }


}
