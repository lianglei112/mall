package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.PmsProductFullReduction;
import com.macro.mall.model.PmsProductLadder;
import com.macro.mall.model.PmsSkuStock;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.PromotionProduct;
import com.macro.mall.portal.service.OmsPromotionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/4 22:05
 * @deprecated 促销管理Service实现类
 */
@Slf4j
@Service
public class OmsPromotionServiceImpl implements OmsPromotionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsPromotionServiceImpl.class);

    @Autowired
    private PortalProductDao portalProductDao;

    @Override
    public List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList) {
        //1、先根据商品id（productId）将购物车（cartItem）中商品进行分组，以spu为单位计算优惠
        Map<Long, List<OmsCartItem>> productCartMap = groupCartItemBySpu(cartItemList);
        //2、查询所有商品的优惠信息
        List<PromotionProduct> promotionProductList = getPromotionProductList(cartItemList);
        //3、根据商品促销类型计算商品促销优惠价格
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        for (Map.Entry<Long, List<OmsCartItem>> entry : productCartMap.entrySet()) {
            Long productId = entry.getKey();
            PromotionProduct promotionProduct = getPromotionProductById(productId, promotionProductList);
            List<OmsCartItem> itemList = entry.getValue();
            Integer promotionType = promotionProduct.getPromotionType();
            //使用促销价
            if (promotionType == 1) {
                for (OmsCartItem item : itemList) {
                    CartPromotionItem cartPromotionItem = new CartPromotionItem();
                    BeanUtils.copyProperties(item, cartPromotionItem);
                    cartPromotionItem.setPromotionMessage("单品促销");
                    //商品原价 - 促销价
                    PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
                    BigDecimal originalPrice = skuStock.getPrice();
                    //单品促销使用原价
                    cartPromotionItem.setPrice(originalPrice);
                    //单品促销优惠价格
                    cartPromotionItem.setReduceAmount(originalPrice.subtract(skuStock.getPromotionPrice()));
                    //商品的真实库存（剩余库存 - 锁定库存）
                    cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
                    //购买商品赠送的积分
                    cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                    //购买商品赠送的成长值
                    cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                    cartPromotionItemList.add(cartPromotionItem);
                }
                //使用阶梯价格
            } else if (promotionType == 3) {
                //计算商品数量
                int count = getCartItemCount(itemList);
                PmsProductLadder ladder = getProductLadder(count, promotionProduct.getProductLadderList());
                if (ladder != null) {
                    for (OmsCartItem item : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(item, cartPromotionItem);
                        String message = getLadderPromotionMessage(ladder);
                        cartPromotionItem.setPromotionMessage(message);
                        //促销活动减去的金额（商品原价 - 折扣*商品原价）
                        PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
                        BigDecimal originalPrice = skuStock.getPrice();
                        BigDecimal reduceAmount = originalPrice.subtract(ladder.getDiscount().multiply(originalPrice));
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                } else {
                    handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
                }
                //使用满减价格
            } else if (promotionType == 4) {
                //满减
                BigDecimal totalAmount = getCartItemAmount(itemList, promotionProductList);
                PmsProductFullReduction fullReduction = getProductFullReduction(totalAmount, promotionProduct.getProductFullReductionList());
                if (fullReduction != null) {
                    for (OmsCartItem item : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(item, cartPromotionItem);
                        String message = getFullReductionPromotionMessage(fullReduction);
                        cartPromotionItem.setPromotionMessage(message);
                        //计算优惠金额：（原价/总价）*满减金额
                        PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
                        BigDecimal originalPrice = skuStock.getPrice();
                        BigDecimal reduceAmount = originalPrice.divide(totalAmount, RoundingMode.HALF_EVEN).multiply(fullReduction.getReducePrice());
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                } else {
                    handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
                }
                //无优惠
            } else {
                handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
            }
        }
        return cartPromotionItemList;
    }

    /**
     * 获取满减促销信息
     *
     * @param fullReduction
     * @return
     */
    private String getFullReductionPromotionMessage(PmsProductFullReduction fullReduction) {
        StringBuilder sb = new StringBuilder();
        sb.append("满减优惠：");
        sb.append("满");
        sb.append(fullReduction.getFullPrice());
        sb.append("元，");
        sb.append("减");
        sb.append(fullReduction.getReducePrice());
        sb.append("元");
        return sb.toString();
    }

    /**
     * 计算满减信息
     *
     * @param totalAmount
     * @param productFullReductionList
     * @return
     */
    private PmsProductFullReduction getProductFullReduction(BigDecimal totalAmount, List<PmsProductFullReduction> productFullReductionList) {
        //满减价格从大到小排序
        productFullReductionList.sort(new Comparator<PmsProductFullReduction>() {
            @Override
            public int compare(PmsProductFullReduction o1, PmsProductFullReduction o2) {
                return o2.getFullPrice().subtract(o1.getFullPrice()).intValue();
            }
        });
        //找到满足满减条件的第一条信息
        for (PmsProductFullReduction fullReduction : productFullReductionList) {
            if (totalAmount.subtract(fullReduction.getFullPrice()).intValue() >= 0) {
                return fullReduction;
            }
        }
        return null;
    }

    /**
     * 获取购物车指定商品的总价
     *
     * @param itemList
     * @param promotionProductList
     * @return
     */
    private BigDecimal getCartItemAmount(List<OmsCartItem> itemList, List<PromotionProduct> promotionProductList) {
        BigDecimal amount = new BigDecimal(0);
        for (OmsCartItem item : itemList) {
            PromotionProduct promotionProduct = getPromotionProductById(item.getProductId(), promotionProductList);
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
            amount = amount.add(skuStock.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return amount;
    }

    /**
     * 对没满足优惠条件的商品进行处理
     *
     * @param cartPromotionItemList
     * @param itemList
     * @param promotionProduct
     */
    private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList, List<OmsCartItem> itemList, PromotionProduct promotionProduct) {
        for (OmsCartItem item : itemList) {
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(item, cartPromotionItem);
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setReduceAmount(BigDecimal.ZERO);
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
            if (skuStock != null) {
                cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
            }
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItemList.add(cartPromotionItem);
        }
    }

    /**
     * 获取打折优惠的促销信息
     *
     * @param ladder
     * @return
     */
    private String getLadderPromotionMessage(PmsProductLadder ladder) {
        StringBuilder sb = new StringBuilder();
        sb.append("打折优惠：");
        sb.append("满");
        sb.append(ladder.getCount());
        sb.append("件，");
        sb.append("打");
        sb.append(ladder.getDiscount().multiply(new BigDecimal(10)));
        sb.append("折");
        return sb.toString();
    }

    /**
     * 根据购买数量获取满足条件的打折优惠策略
     *
     * @param count
     * @param productLadderList
     * @return
     */
    private PmsProductLadder getProductLadder(int count, List<PmsProductLadder> productLadderList) {
        //按照数量从大到小排序
        productLadderList.sort(new Comparator<PmsProductLadder>() {
            @Override
            public int compare(PmsProductLadder o1, PmsProductLadder o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        for (PmsProductLadder productLadders : productLadderList) {
            if (count >= productLadders.getCount()) {
                return productLadders;
            }
        }
        return null;
    }

    /**
     * 计算购物车中对应商品的数量
     *
     * @param itemList
     * @return
     */
    private int getCartItemCount(List<OmsCartItem> itemList) {
        int count = 0;
        for (OmsCartItem item : itemList) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * 获取商品的原价
     *
     * @param promotionProduct
     * @param productSkuId
     * @return
     */
    private PmsSkuStock getOriginalPrice(PromotionProduct promotionProduct, Long productSkuId) {
        for (PmsSkuStock skuStock : promotionProduct.getSkuStockList()) {
            if (productSkuId.equals(skuStock.getId())) {
                return skuStock;
            }
        }
        return null;
    }

    /**
     * 根据商品id获取商品的促销信息
     *
     * @param productId
     * @param promotionProductList
     * @return
     */
    private PromotionProduct getPromotionProductById(Long productId, List<PromotionProduct> promotionProductList) {
        if (!CollectionUtils.isEmpty(promotionProductList)) {
            for (PromotionProduct promotionProduct : promotionProductList) {
                if (productId.equals(promotionProduct.getId())) {
                    return promotionProduct;
                }
            }
        }
        return null;
    }

    /**
     * @param cartItemList
     * @return
     */
    private List<PromotionProduct> getPromotionProductList(List<OmsCartItem> cartItemList) {
        List<Long> productIdList = new ArrayList<>();
        if (CollUtil.isNotEmpty(cartItemList)) {
            productIdList = cartItemList.stream().map(item -> {
                return item.getProductId();
            }).collect(Collectors.toList());
        }
        return portalProductDao.getPromotionProductList(productIdList);
    }

    /**
     * 以spu为单位对购物车中商品进行分组
     *
     * @param cartItemList
     * @return
     */
    private Map<Long, List<OmsCartItem>> groupCartItemBySpu(List<OmsCartItem> cartItemList) {
        Map<Long, List<OmsCartItem>> productCartMap = new TreeMap<>();
        if (CollUtil.isNotEmpty(cartItemList)) {
            for (OmsCartItem cartItem : cartItemList) {
                List<OmsCartItem> productCartItemList = productCartMap.get(cartItem.getProductId());
                if (productCartItemList == null) {
                    productCartItemList = new ArrayList<>();
                    productCartItemList.add(cartItem);
                    productCartMap.put(cartItem.getProductId(), productCartItemList);
                } else {
                    productCartItemList.add(cartItem);
                }
            }
        }
        return productCartMap;
    }
}
