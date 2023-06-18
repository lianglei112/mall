package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.UmsAdminParam;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsRole;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.service.UmsRoleService;
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
 * @date 2023/6/18 19:55
 * @deprecated 后台用户管理Controller控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
@Tag(name = "UmsAdminController", description = "后台用户管理控制器")
@Api(tags = "UmsAdminController", description = "后台用户管理控制器")
public class UmsAdminController {


    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminController.class);

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsRoleService umsRoleService;


    /**
     * 用户注册
     *
     * @param umsAdminParam
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed("注册失败，请稍后重试！");
        }
        return CommonResult.success(umsAdmin);
    }

    /**
     * 根据用户名或姓名分页获取用户列表
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsAdmin> adminList = umsAdminService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    /**
     * 获取指定用户信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取用户指定信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin umsAdmin = umsAdminService.getItem(id);
        return CommonResult.success(umsAdmin);
    }


    /**
     * 修改指定用户信息
     *
     * @param id
     * @param admin
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = umsAdminService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除指定用户信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        int count = umsAdminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改账号状态
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改账号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(id);
        int count = umsAdminService.update(id, umsAdmin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取指定用户的角色
     *
     * @param adminId
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.POST)
    public CommonResult<List<UmsRole>> getRoleList(Long adminId) {
        List<UmsRole> umsRoleList = umsRoleService.getRoleList(adminId);
        return CommonResult.success(umsRoleList);
    }

    @ResponseBody
    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = umsRoleService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
