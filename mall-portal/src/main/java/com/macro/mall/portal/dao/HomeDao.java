package com.macro.mall.portal.dao;

import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.SmsHomeBrand;
import com.macro.mall.model.SmsHomeNewProduct;
import com.macro.mall.portal.domain.FlashPromotionProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/18 21:50
 * @deprecated 首页内容管理自定义Dao持久层
 */
@Repository
public interface HomeDao {

    List<SmsHomeBrand> getRecommendBrandList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<FlashPromotionProduct> getFlashProductList(@Param("flashPromotionId") Long flashPromotionId, @Param("sessionId") Long sessionId);

    List<SmsHomeNewProduct> getNewProductList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    List<PmsProduct> getHotProductList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    List<CmsSubject> getRecommendSubjectList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
