package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMenu;
import com.macro.mall.service.UmsMenuService;
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
 * @date 2023/6/19 22:34
 * @deprecated 后台菜单管理controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@Api(tags = "UmsMenuController")
@Tag(name = "UmsMenuController", description = "后台菜单管理")
public class UmsMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMenuController.class);

    @Autowired
    private UmsMenuService umsMenuService;

    /**
     * 添加后台菜单
     *
     * @param umsMenu
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "添加后台菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsMenu umsMenu) {
        int count = umsMenuService.create(umsMenu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新增失败，请稍后重试！");
    }

    /**
     * 修改后台菜单
     *
     * @param id
     * @param umsMenu
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改后台菜单")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsMenu umsMenu) {
        int count = umsMenuService.update(id, umsMenu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改失败，请稍后重试！");
    }

    /**
     * 根据ID获取后台菜单
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据ID获取后台菜单")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<UmsMenu> getItem(@PathVariable Long id) {
        UmsMenu umsMenu = umsMenuService.getItem(id);
        return CommonResult.success(umsMenu);
    }

    /**
     * 根据ID删除后台菜单
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据ID删除后台菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        int count = umsMenuService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("刪除失败，请稍后重试！");
    }

    /**
     * 分页查询后台菜单
     *
     * @param parentId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "分页查询后台菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsMenu>> list(@PathVariable Long parentId,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsMenu> umsMenuList = umsMenuService.list(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(umsMenuList));
    }


    /**
     * 修改菜单显示状态
     *
     * @param id
     * @param hidden
     * @return
     */
    @ResponseBody
    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
    public CommonResult updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        int count = umsMenuService.updateHidden(id, hidden);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

}
