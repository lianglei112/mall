package com.macro.mall.service;

import com.macro.mall.model.SmsHomeRecommendProduct;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:17
 * @deprecated 首页人气推荐管理Service
 */
public interface SmsHomeRecommendProductService {

    int create(List<SmsHomeRecommendProduct> homeRecommendProductList);

    int updateSort(Long id, Integer sort);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
