package com.macro.mall.dao;

import com.macro.mall.model.SmsCouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/17 21:38
 * @deprecated 优惠券关联商品分类持久层
 */
@Repository
public interface SmsCouponProductCategoryRelationDao {

    int insertList(@Param("list") List<SmsCouponProductCategoryRelation> productCategoryRelationList);

}
