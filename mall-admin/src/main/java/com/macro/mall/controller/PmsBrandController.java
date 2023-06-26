package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.service.PmsBrandService;
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
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 9:59
 * @description 商品品牌管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/brand")
@Tag(name = "PmsBrandController", description = "商品品牌管理控制器")
@Api(tags = "PmsBrandController", description = "商品品牌管理控制器")
public class PmsBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 获取全部品牌列表
     *
     * @return
     */
    @ApiOperation(value = "获取全部品牌列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> listAll() {
        List<PmsBrand> pmsBrandList = pmsBrandService.listAll();
        return CommonResult.success(pmsBrandList);
    }

    /**
     * 根据品牌名称分页获取品牌列表
     *
     * @param keyword
     * @param showStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> list(@RequestParam(value = "", defaultValue = "") String keyword,
                                                   @RequestParam(value = "", defaultValue = "") Integer showStatus,
                                                   @RequestParam(value = "", defaultValue = "") Integer pageNum,
                                                   @RequestParam(value = "", defaultValue = "") Integer pageSize) {
        List<PmsBrand> pmsBrandList = pmsBrandService.list(keyword, showStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsBrandList));
    }

    /**
     * 添加品牌
     *
     * @param pmsBrandParam
     * @return
     */
    @ApiOperation(value = "添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@Valid @RequestBody PmsBrandParam pmsBrandParam) {
        int count = pmsBrandService.create(pmsBrandParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加品牌失败，请稍后重试！");
    }

    /**
     * 根据编号查询品牌信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据编号查询品牌信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<PmsBrand> getItem(@PathVariable Long id) {
        PmsBrand pmsBrand = pmsBrandService.getItem(id);
        return CommonResult.success(pmsBrand);
    }

    /**
     * 更新品牌
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "更新品牌")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PmsBrandParam pmsBrandParam) {
        int count = pmsBrandService.update(id, pmsBrandParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("更新品牌信息失败，请稍后重试！");
    }

    /**
     * 删除品牌
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = pmsBrandService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除品牌失败，请稍后重试！");
    }

    /**
     * 批量删除品牌
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除品牌")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteBatch(@RequestParam(value = "ids") List<Long> ids) {
        int count = pmsBrandService.deleteBatch(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除品牌失败，请稍后重试！");
    }

    /**
     * 批量更新显示状态
     *
     * @param ids
     * @param showStatus
     * @return
     */
    @ApiOperation(value = "批量更新显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("showStatus") Integer showStatus) {
        int count = pmsBrandService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量更新显示状态失败，请稍后重试！");
    }

    /**
     * 批量更新厂家制造商状态
     *
     * @param ids
     * @param factoryStatus
     * @return
     */
    @ApiOperation(value = "批量更新厂家制造商状态")
    @RequestMapping(value = "/update/factoryStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateFactoryStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("factoryStatus") Integer factoryStatus) {
        int count = pmsBrandService.updateFactoryStatus(ids, factoryStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量更新厂家制造商状态失败，请稍后重试！");
    }
}