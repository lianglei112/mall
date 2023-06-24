package com.macro.mall.dao;

import com.macro.mall.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 23:34
 * @deprecated 审核记录操作持久层
 */
@Repository
public interface PmsProductVertifyRecordDao {

    /**
     * 批量插入审核记录
     *
     * @param list
     * @return
     */
    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
