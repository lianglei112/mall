package com.macro.mall.dao;

import com.macro.mall.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 18:25
 * @deprecated 关联主题操作持久层
 */
@Repository
public interface CmsPrefrenceAreaProductRelationDao {

    /**
     * 执行关联主题的批量插入
     *
     * @param dataList
     * @return
     */
    int insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> dataList);

}
