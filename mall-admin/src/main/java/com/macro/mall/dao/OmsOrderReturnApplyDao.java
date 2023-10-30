package com.macro.mall.dao;

import com.macro.mall.dto.OmsOrderReturnApplyResult;
import com.macro.mall.dto.OmsReturnApplyQueryParam;
import com.macro.mall.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 22:31
 * @deprecated 订单退货相关操作持久层
 */
@Repository
public interface OmsOrderReturnApplyDao {


    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);


    OmsOrderReturnApplyResult getDetail(@Param("id") Long id);

}
