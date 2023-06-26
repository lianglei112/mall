package com.macro.mall.service;

import com.macro.mall.model.PmsSkuStock;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 11:29
 * @description sku库存信息service层
 */
public interface PmsSkuStockService {

    List<PmsSkuStock> getList(Long pid, String keyword);

    int update(Long pid, List<PmsSkuStock> stockList);
}
