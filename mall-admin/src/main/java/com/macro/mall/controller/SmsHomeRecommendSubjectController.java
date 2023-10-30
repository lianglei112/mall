package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.SmsHomeRecommendSubject;
import com.macro.mall.service.SmsHomeRecommendSubjectService;
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
 * @date 2023/7/16 22:24
 * @deprecated 首页专题推荐管理Controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/home/recommendSubject")
@Tag(name = "SmsHomeRecommendSubjectController", description = "首页专题推荐管理")
@Api(tags = "SmsHomeRecommendSubjectController", description = "首页专题推荐管理")
public class SmsHomeRecommendSubjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsHomeRecommendProductController.class);

    @Autowired
    private SmsHomeRecommendSubjectService smsHomeRecommendSubjectService;

    /**
     * 添加首页推荐专题
     *
     * @param homeRecommendSubjectList
     * @return
     */
    @ApiOperation("添加首页推荐专题")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody List<SmsHomeRecommendSubject> homeRecommendSubjectList) {
        int count = smsHomeRecommendSubjectService.create(homeRecommendSubjectList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加首页推荐专题失败，请稍后重试！");
    }

    /**
     * 修改推荐排序
     *
     * @param id
     * @param sort
     * @return
     */
    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateSort(@PathVariable Long id, Integer sort) {
        int count = smsHomeRecommendSubjectService.updateSort(id, sort);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改推荐排序失败，请稍后重试！");
    }

    /**
     * 批量删除推荐
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeRecommendSubjectService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量删除推荐失败，请稍后重试！");
    }

    /**
     * 批量修改推荐状态
     *
     * @param ids
     * @param recommendStatus
     * @return
     */
    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = smsHomeRecommendSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("批量修改推荐状态失败，请稍后重试！");
    }

    /**
     * 分页查询推荐
     *
     * @param subjectName
     * @param recommendStatus
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsHomeRecommendSubject>> list(@RequestParam(value = "subjectName", required = false) String subjectName,
                                                                  @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsHomeRecommendSubject> homeRecommendSubjectList = smsHomeRecommendSubjectService.list(subjectName, recommendStatus, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(homeRecommendSubjectList));
    }

}
