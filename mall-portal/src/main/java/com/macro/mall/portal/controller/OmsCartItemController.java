package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.UmsMemberService;
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
 * @date 2023/6/29 21:17
 * @deprecated 购物车管理控制器
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
@Tag(name = "OmsCartItemController", description = "购物车管理控制器")
@Api(tags = "OmsCartItemController", description = "购物车管理控制器")
public class OmsCartItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemController.class);

    @Autowired
    private OmsCartItemService omsCartItemService;

    @Autowired
    private UmsMemberService umsMemberService;

    /**
     * 添加商品到购物车
     *
     * @param omsCartItem
     * @return
     */
    @ApiOperation(value = "添加商品到购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OmsCartItem omsCartItem) {
        int count = omsCartItemService.add(omsCartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("添加购物车失败，请稍后重试！");
    }

    /**
     * 删除购物车中的某个商品
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除购物车中的某个商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = omsCartItemService.delete(umsMemberService.getCurrentMember().getId(), ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除失败，请稍后重试！");
    }

    /**
     * 获取某个会员的购物车列表
     *
     * @return
     */
    @ApiOperation(value = "获取某个会员的购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsCartItem>> list() {
        List<OmsCartItem> omsCartItemList = omsCartItemService.list(umsMemberService.getCurrentMember().getId());
        return CommonResult.success(omsCartItemList);
    }

    /**
     * 获取某个会员的购物车列表,包括促销信息
     *
     * @param cartIds
     * @return
     */
    @ApiOperation(value = "获取某个会员的购物车列表,包括促销信息")
    @RequestMapping(value = "/list/promotion", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CartPromotionItem>> listPromotion(@RequestParam(required = false) List<Long> cartIds) {
        List<CartPromotionItem> cartPromotionItemList = omsCartItemService.listPromotion(umsMemberService.getCurrentMember().getId(), cartIds);
        return CommonResult.success(cartPromotionItemList);
    }

    /**
     * 修改购物车中某个商品的数量
     *
     * @param id
     * @param quantity
     * @return
     */
    @ApiOperation(value = "修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateQuantity(@RequestParam(value = "id") Long id,
                                       @RequestParam(value = "quantity") Integer quantity) {
        int count = omsCartItemService.updateQuantity(id, umsMemberService.getCurrentMember().getId(), quantity);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改某个商品的数量失败，请稍后重试！");
    }

    /**
     * 获取购物车中某个商品的规格,用于重选规格
     *
     * @param productId
     * @return
     */
    @ApiOperation("获取购物车中某个商品的规格,用于重选规格")
    @RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CartProduct> getCartProduct(@PathVariable Long productId) {
        CartProduct cartProduct = omsCartItemService.getCartProduct(productId);
        return CommonResult.success(cartProduct);
    }

    /**
     * 修改购物车中商品的规格
     *
     * @param omsCartItem
     * @return
     */
    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "/update/attr", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAttr(@RequestBody OmsCartItem omsCartItem) {
        int count = omsCartItemService.updateAttr(omsCartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改某个商品的数量失败，请稍后重试！");
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @ApiOperation("清空购物车")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult clear() {
        int count = omsCartItemService.clear(umsMemberService.getCurrentMember().getId());
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("清空购物车失败，请稍后重试！");
    }

}
