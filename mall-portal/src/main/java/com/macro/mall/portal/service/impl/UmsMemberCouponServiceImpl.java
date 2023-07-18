package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.common.exception.Asserts;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.dao.SmsCouponHistoryDao;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import com.macro.mall.portal.service.UmsMemberCouponService;
import com.macro.mall.portal.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/7 22:15
 * @deprecated 用户优惠券管理service层实现类
 */
@Slf4j
@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberCouponServiceImpl.class);

    @Autowired
    private UmsMemberService umsMemberService;

    @Autowired
    private SmsCouponHistoryDao smsCouponHistoryDao;

    @Autowired
    private SmsCouponMapper smsCouponMapper;

    @Autowired
    private SmsCouponHistoryMapper smsCouponHistoryMapper;

    @Autowired
    private SmsCouponProductRelationMapper smsCouponProductRelationMapper;

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private SmsCouponProductCategoryRelationMapper smsCouponProductCategoryRelationMapper;

    @Override
    public List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        Date now = new Date();
        //获取该用户所有优惠券
        List<SmsCouponHistoryDetail> allList = smsCouponHistoryDao.getDetailList(currentMember.getId());
        //根据优惠券使用类型来判断优惠券是否可用
        List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
        List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
        for (SmsCouponHistoryDetail couponHistoryDetail : allList) {
            Integer useType = couponHistoryDetail.getCoupon().getUseType();
            BigDecimal minPoint = couponHistoryDetail.getCoupon().getMinPoint();
            Date endTime = couponHistoryDetail.getCoupon().getEndTime();
            //0、全场通用
            if (useType.equals(0)) {
                //判断是否满足优惠起点
                //计算购物车总价
                BigDecimal totalAmount = calcTotalAmount(cartPromotionItemList);
                if (now.before(endTime) && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
                //1、指定分类
            } else if (useType.equals(1)) {
                //计算指定分类商品的总价
                List<Long> productCategoryIds = new ArrayList<>();
                for (SmsCouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                    productCategoryIds.add(categoryRelation.getProductCategoryId());
                }
                BigDecimal totalAmount = calcTotalAmountByproductCategoryId(cartPromotionItemList, productCategoryIds);
                if (now.before(endTime) && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
                //2、指定商品
            } else if (useType.equals(2)) {
                //计算指定商品的总价
                List<Long> productIds = new ArrayList<>();
                for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                    productIds.add(productRelation.getProductId());
                }
                BigDecimal totalAmount = calcTotalAmountByProductId(cartPromotionItemList, productIds);
                if (now.before(endTime) && totalAmount.subtract(minPoint).intValue() >= 0) {
                    enableList.add(couponHistoryDetail);
                } else {
                    disableList.add(couponHistoryDetail);
                }
            }
        }
        //根据传递的参数来判断用户是否使用可用优惠券还是不可用优惠券
        if (type.equals(1)) {
            return enableList;
        } else {
            return disableList;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void add(Long couponId) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        //获取优惠券信息，判断数量
        SmsCoupon smsCoupon = smsCouponMapper.selectByPrimaryKey(couponId);
        if (smsCoupon == null) {
            Asserts.fail("优惠券不存在！");
        }
        if (smsCoupon.getCount() <= 0) {
            Asserts.fail("优惠券已经领取完了！");
        }
        Date now = new Date();
        if (now.before(smsCoupon.getEnableTime())) {
            Asserts.fail("优惠券还没到领取时间！");
        }
        //判断用户领取的优惠券数量是否超过限制
        SmsCouponHistoryExample historyExample = new SmsCouponHistoryExample();
        historyExample.createCriteria().andCouponIdEqualTo(couponId).andMemberIdEqualTo(currentMember.getId());
        long count = smsCouponHistoryMapper.countByExample(historyExample);
        if (count >= smsCoupon.getPerLimit()) {
            Asserts.fail("您已经领取过该优惠券了！");
        }
        //生成领取优惠券历史
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setCouponId(couponId);
        couponHistory.setMemberId(currentMember.getId());
        couponHistory.setMemberNickname(currentMember.getNickname());
        couponHistory.setCreateTime(new Date());
        couponHistory.setCouponCode(generateCouponCode(currentMember.getId()));
        //主动领取
        couponHistory.setGetType(1);
        //未使用
        couponHistory.setUseStatus(0);
        smsCouponHistoryMapper.insert(couponHistory);
        //修改优惠券表的数量、领取数量
        smsCoupon.setCount(smsCoupon.getCount() - 1);
        smsCoupon.setReceiveCount(smsCoupon.getReceiveCount() == null ? 1 : smsCoupon.getReceiveCount() + 1);
        smsCouponMapper.updateByPrimaryKey(smsCoupon);
    }

    @Override
    public List<SmsCouponHistory> listHistory(Integer useStatus) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(currentMember.getId());
        if (useStatus != null) {
            criteria.andUseStatusEqualTo(useStatus);
        }
        return smsCouponHistoryMapper.selectByExample(example);
    }

    @Override
    public List<SmsCoupon> list(Integer useStatus) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        return smsCouponHistoryDao.getCouponList(currentMember.getId(), useStatus);
    }

    @Override
    public List<SmsCoupon> listByProduct(Long productId) {
        List<Long> allCouponIds = new ArrayList<>();
        //获取指定商品优惠券
        SmsCouponProductRelationExample cprExample = new SmsCouponProductRelationExample();
        cprExample.createCriteria().andProductIdEqualTo(productId);
        List<SmsCouponProductRelation> cprList = smsCouponProductRelationMapper.selectByExample(cprExample);
        if (CollUtil.isNotEmpty(cprList)) {
            List<Long> couponIds = cprList.stream().map(SmsCouponProductRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //获取指定分类优惠券
        PmsProduct product = pmsProductMapper.selectByPrimaryKey(productId);
        SmsCouponProductCategoryRelationExample cpcrExample = new SmsCouponProductCategoryRelationExample();
        cpcrExample.createCriteria().andProductCategoryIdEqualTo(product.getProductCategoryId());
        List<SmsCouponProductCategoryRelation> cpcrList = smsCouponProductCategoryRelationMapper.selectByExample(cpcrExample);
        if (CollUtil.isNotEmpty(cpcrList)) {
            List<Long> couponIds = cpcrList.stream().map(SmsCouponProductCategoryRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //查询所有优惠劵
        SmsCouponExample couponExample = new SmsCouponExample();
        couponExample.createCriteria().andEndTimeGreaterThan(new Date())
                .andStartTimeLessThan(new Date())
                .andUseTypeEqualTo(0);
        if (CollUtil.isNotEmpty(allCouponIds)) {
            couponExample.or(couponExample.createCriteria()
                    .andEndTimeGreaterThan(new Date())
                    .andStartTimeEqualTo(new Date())
                    .andUseTypeNotEqualTo(0)
                    .andIdIn(allCouponIds));
        }
        return smsCouponMapper.selectByExample(couponExample);
    }

    /**
     * 生成coupon_code码
     *
     * @param memberId
     * @return
     */
    private String generateCouponCode(Long memberId) {
        StringBuilder sb = new StringBuilder();
        Long currentTimeMillis = System.currentTimeMillis();
        String timeMillisStr = currentTimeMillis.toString();
        sb.append(timeMillisStr.substring(timeMillisStr.length() - 8));
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        String memberIdStr = memberId.toString();
        if (memberIdStr.length() <= 4) {
            sb.append(String.format("%04d", memberId));
        } else {
            sb.append(memberIdStr.substring(memberIdStr.length() - 4));
        }
        return sb.toString();
    }

    /**
     * 计算指定商品的总价
     *
     * @param cartPromotionItemList
     * @param productIds
     * @return
     */
    private BigDecimal calcTotalAmountByProductId(List<CartPromotionItem> cartPromotionItemList, List<Long> productIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            if (productIds.contains(item.getProductId())) {
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total = total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    /**
     * 计算指定商品分类的总价
     *
     * @param cartPromotionItemList
     * @param productCategoryIds
     * @return
     */
    private BigDecimal calcTotalAmountByproductCategoryId(List<CartPromotionItem> cartPromotionItemList, List<Long> productCategoryIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            //指定分类下的得商品的价格进行相加
            if (productCategoryIds.contains(item.getProductCategoryId())) {
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total = total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    /**
     * 计算购物车商品的总价
     *
     * @param cartPromotionItemList
     * @return
     */
    private BigDecimal calcTotalAmount(List<CartPromotionItem> cartPromotionItemList) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
            total = total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }
}
