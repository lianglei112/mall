package com.macro.mall.dao;

import com.macro.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/24 21:07
 * @deprecated 商品信息操作持久层
 */
@Repository
public interface PmsProductDao {


    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
