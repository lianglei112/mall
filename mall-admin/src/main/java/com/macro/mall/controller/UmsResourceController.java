package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsResource;
import com.macro.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 11:01
 * @description 后台资源管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/resource")
@Api(tags = "UmsResourceController")
@Tag(name = "UmsResourceController", description = "后台资源管理")
public class UmsResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsResourceController.class);

    @Autowired
    private UmsResourceService umsResourceService;

    /**
     * 添加后台菜单
     *
     * @param umsResource
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody UmsResource umsResource) {
        int count = umsResourceService.create(umsResource);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加资源失败，请稍后重试！");
    }

    /**
     * 修改后台资源
     *
     * @param id
     * @param umsResource
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsResource umsResource) {
        int count = umsResourceService.update(id, umsResource);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改后台资源失败，请稍后重试！");
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
    public CommonResult delete(@PathVariable Long id) {
        int count = umsResourceService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除失败，请稍后重试！");
    }

    /**
     * 根据ID获取后台资源详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据ID获取后台资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = umsResourceService.getItem(id);
        return CommonResult.success(umsResource);
    }

    /**
     * 查询所有后台资源
     *
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UmsResource>> listAll() {
        List<UmsResource> umsResourceList = umsResourceService.listAll();
        return CommonResult.success(umsResourceList);
    }

    @ResponseBody
    @ApiOperation(value = "查询所有后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsResource>> list(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                                      @RequestParam(value = "nameKeyWord", required = false) String nameKeyWord,
                                                      @RequestParam(value = "urlKeyword", required = false) String urlKeyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<UmsResource> umsResourceList = umsResourceService.list(categoryId, nameKeyWord, urlKeyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(umsResourceList));
    }
}
