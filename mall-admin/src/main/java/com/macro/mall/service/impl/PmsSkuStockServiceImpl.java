package com.macro.mall.service.impl;

import com.macro.mall.dao.PmsSkuStockDao;
import com.macro.mall.mapper.PmsSkuStockMapper;
import com.macro.mall.model.PmsSkuStock;
import com.macro.mall.model.PmsSkuStockExample;
import com.macro.mall.service.PmsSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 11:31
 * @description sku库存信息service实现层
 */
@Slf4j
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsSkuStockServiceImpl.class);

    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria();
        if (pid != null) {
            criteria.andProductIdEqualTo(pid);
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andSkuCodeLike("%" + keyword + "%");
        }
        return pmsSkuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> stockList) {
        return pmsSkuStockDao.replaceList(stockList);
    }
}
