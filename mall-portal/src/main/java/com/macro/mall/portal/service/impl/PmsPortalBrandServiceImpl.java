package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.portal.service.PmsPortalBrandService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 13:51
 * @description 前台品牌管理service实现类
 */
@Slf4j
@Service
public class PmsPortalBrandServiceImpl implements PmsPortalBrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsPortalBrandServiceImpl.class);

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    public PmsBrand detail(Long brandId) {
        return pmsBrandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public List<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(brandId)
                .andPublishStatusEqualTo(1)
                .andDeleteStatusEqualTo(0);
        List<PmsProduct> pmsProductList = pmsProductMapper.selectByExample(example);
        return pmsProductList;
    }


}
