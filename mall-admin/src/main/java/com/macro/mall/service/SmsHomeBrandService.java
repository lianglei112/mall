package com.macro.mall.service;

import com.macro.mall.model.SmsHomeBrand;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 21:54
 * @deprecated 首页品牌管理Service
 */
public interface SmsHomeBrandService {

    int create(List<SmsHomeBrand> homeBrandList);

    int updateSort(Long id, Integer sort);

    int delete(List<Long> ids);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
