package com.macro.mall.service;

import com.macro.mall.model.SmsHomeNewProduct;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:07
 * @deprecated 首页新品推荐管理Service
 */
public interface SmsHomeNewProductService {

    int create(List<SmsHomeNewProduct> homeNewProductList);

    int updateSort(Long id, Integer sort);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
