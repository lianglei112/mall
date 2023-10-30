package com.macro.mall.dao;

import com.macro.mall.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/27 22:33
 * @deprecated 订单操作记录相关操作持久层
 */
@Repository
public interface OmsOrderOperateHistoryDao {

    int insertList(@Param("list") List<OmsOrderOperateHistory> collect);
}
