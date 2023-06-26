package com.macro.mall.service;

import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsProduct;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/18 20:13
 * @deprecated 商品操作service层
 */
public interface PmsProductService {

    int create(PmsProductParam pmsProductParam);

    List<PmsProduct> getList(PmsProductQueryParam productQueryParam, Integer pageNum, Integer pageSize);

    PmsProductResult getUpdateInfo(Long id);

    int update(Long id, PmsProductParam pmsProductParam);

    List<PmsProduct> list(String keyword);

    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

}
