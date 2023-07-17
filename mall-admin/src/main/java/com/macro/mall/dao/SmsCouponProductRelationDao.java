package com.macro.mall.dao;

import com.macro.mall.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/17 21:37
 * @deprecated 优惠券关联商品相关操作持久层
 */
@Repository
public interface SmsCouponProductRelationDao {


    int insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
}
