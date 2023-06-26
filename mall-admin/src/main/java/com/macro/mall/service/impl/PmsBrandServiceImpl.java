package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsBrandExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.service.PmsBrandService;
import com.macro.mall.service.PmsProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 10:04
 * @description 商品品牌管理service实现类
 */
@Slf4j
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandServiceImpl.class);

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    public List<PmsBrand> listAll() {
        return pmsBrandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int create(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        return pmsBrandMapper.insertSelective(pmsBrand);
    }

    @Override
    public List<PmsBrand> list(String keyword, Integer showStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example = new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        if (showStatus != null) {
            criteria.andShowStatusEqualTo(showStatus);
        }
        return pmsBrandMapper.selectByExample(example);
    }

    @Override
    public PmsBrand getItem(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int update(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        //更新品牌时要更新商品中的品牌名称
        PmsProduct pmsProduct = new PmsProduct();
        pmsProduct.setBrandName(pmsBrand.getName());
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andBrandIdEqualTo(id);
        pmsProductMapper.updateByExampleSelective(pmsProduct, productExample);
        return pmsBrandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int delete(Long id) {
        return pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.deleteByExample(pmsBrandExample);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand, example);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);
        PmsBrandExample example = new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand,example);
    }


}
