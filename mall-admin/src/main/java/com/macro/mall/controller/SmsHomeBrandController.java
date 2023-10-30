package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeBrand;
import com.macro.mall.service.SmsHomeBrandService;
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
 * @date 2023/7/16 21:52
 * @deprecated 首页品牌管理Controller 控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/home/brand")
@Tag(name = "SmsHomeBrandController", description = "首页品牌管理")
@Api(tags = "SmsHomeBrandController", description = "首页品牌管理")
public class SmsHomeBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsHomeBrandController.class);

    @Autowired
    private SmsHomeBrandService smsHomeBrandService;

    /**
     * 添加首页推荐品牌
     *
     * @param homeBrandList
     * @return
     */
    @ApiOperation("添加首页推荐品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody List<SmsHomeBrand> homeBrandList) {
        int count = smsHomeBrandService.create(homeBrandList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加首页推荐品牌失败，请稍后重试！");
    }

    /**
     * 修改推荐品牌排序
     *
     * @param id
     * @param sort
     * @return
     */
    @ApiOperation("修改推荐品牌排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateSort(@PathVariable Long id, Integer sort) {
        int count = smsHomeBrandService.updateSort(id, sort);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改推荐品牌排序失败，请稍后重试！");
    }

    /**
     * 批量删除推荐品牌
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除推荐品牌")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeBrandService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除推荐品牌失败，请稍后重试！");
    }

    /**
     * 批量修改推荐品牌状态
     *
     * @param ids
     * @param recommendStatus
     * @return
     */
    @ApiOperation("批量修改推荐品牌状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = smsHomeBrandService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改推荐品牌状态失败，请稍后重试！");
    }

    /**
     * 分页查询推荐品牌
     *
     * @param brandName
     * @param recommendStatus
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询推荐品牌")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsHomeBrand>> list(@RequestParam(value = "brandName", required = false) String brandName,
                                                       @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsHomeBrand> homeBrandList = smsHomeBrandService.list(brandName, recommendStatus, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(homeBrandList));
    }
}
