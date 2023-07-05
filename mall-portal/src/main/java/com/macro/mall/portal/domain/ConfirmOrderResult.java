package com.macro.mall.portal.domain;

import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberReceiveAddress;
import lombok.*;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/5 22:44
 * @deprecated 确认单信息封装
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class ConfirmOrderResult {

    /**
     * 包含优惠信息的购物车信息
     */
    private List<CartPromotionItem> cartPromotionItemList;

    /**
     * 用户收货地址列表
     */
    private List<UmsMemberReceiveAddress> memberReceiveAddressList;

    /**
     * 用户可以优惠券列表
     */
    private List<SmsCouponHistoryDetail> couponHistoryDetailList;

    /**
     * 积分使用规则
     */
    private UmsIntegrationConsumeSetting integrationConsumeSetting;

    /**
     * 会员持有的积分
     */
    private Integer memberIntegration;

    /**
     * 计算的金额
     */
    private CalcAmount calcAmount;

    public List<CartPromotionItem> getCartPromotionItemList() {
        return cartPromotionItemList;
    }

    public void setCartPromotionItemList(List<CartPromotionItem> cartPromotionItemList) {
        this.cartPromotionItemList = cartPromotionItemList;
    }

    public List<UmsMemberReceiveAddress> getMemberReceiveAddressList() {
        return memberReceiveAddressList;
    }

    public void setMemberReceiveAddressList(List<UmsMemberReceiveAddress> memberReceiveAddressList) {
        this.memberReceiveAddressList = memberReceiveAddressList;
    }

    public List<SmsCouponHistoryDetail> getCouponHistoryDetailList() {
        return couponHistoryDetailList;
    }

    public void setCouponHistoryDetailList(List<SmsCouponHistoryDetail> couponHistoryDetailList) {
        this.couponHistoryDetailList = couponHistoryDetailList;
    }

    public UmsIntegrationConsumeSetting getIntegrationConsumeSetting() {
        return integrationConsumeSetting;
    }

    public void setIntegrationConsumeSetting(UmsIntegrationConsumeSetting integrationConsumeSetting) {
        this.integrationConsumeSetting = integrationConsumeSetting;
    }

    public Integer getMemberIntegration() {
        return memberIntegration;
    }

    public void setMemberIntegration(Integer memberIntegration) {
        this.memberIntegration = memberIntegration;
    }

    public CalcAmount getCalcAmount() {
        return calcAmount;
    }

    public void setCalcAmount(CalcAmount calcAmount) {
        this.calcAmount = calcAmount;
    }
}
