package com.macro.mall.dao;

import com.macro.mall.dto.SmsCouponParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/17 21:36
 * @deprecated 优惠券相关操作持久层
 */
@Repository
public interface SmsCouponDao {

    SmsCouponParam getItem(@Param("id") Long id);

}
