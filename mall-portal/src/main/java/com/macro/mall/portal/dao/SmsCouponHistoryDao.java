package com.macro.mall.portal.dao;

import com.macro.mall.model.SmsCoupon;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/7 22:30
 * @deprecated 会员优惠券领取记录管理自定义Dao层
 */
public interface SmsCouponHistoryDao {

    List<SmsCouponHistoryDetail> getDetailList(@Param("memberId") Long id);

    List<SmsCoupon> getCouponList(@Param("memberId") Long memberId, @Param("useStatus") Integer useStatus);
}
