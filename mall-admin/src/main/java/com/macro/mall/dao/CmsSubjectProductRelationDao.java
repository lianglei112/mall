package com.macro.mall.dao;

import com.macro.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 关联优选操作持久层
 */
@Repository
public interface CmsSubjectProductRelationDao {

    /**
     * 执行关联优选批量插入操作
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<CmsSubjectProductRelation> dataList);
}


