package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.PmsProductCategoryAttributeRelationDao;
import com.macro.mall.dto.PmsProductCategoryParam;
import com.macro.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.macro.mall.mapper.PmsProductCategoryMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.PmsProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/25/ 13:46
 * @description 商品管理service实现类
 */
@Slf4j
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductCategoryServiceImpl.class);

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationDao pmsProductCategoryAttributeRelationDao;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper pmsProductCategoryAttributeRelationMapper;

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return pmsProductCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setProductCount(0);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        pmsProductCategoryMapper.insert(pmsProductCategory);
        //没有父分类时设置为一级分类
        setCategoryLevel(pmsProductCategory);
        int count = pmsProductCategoryMapper.insertSelective(pmsProductCategory);
        //创建筛选属性关联
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            insertRelationList(pmsProductCategory.getId(), productAttributeIdList);
        }
        return count;
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int update(Long id, PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(id);
        setCategoryLevel(pmsProductCategory);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        //更新商品分类时要更新商品中的名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(pmsProductCategoryParam.getName());
        PmsProductExample productExample = new PmsProductExample();
        productExample.createCriteria().andProductCategoryIdEqualTo(id);
        pmsProductMapper.updateByExample(product, productExample);
        //同时更新筛选属性的信息
        if (!CollectionUtils.isEmpty(pmsProductCategoryParam.getProductAttributeIdList())) {
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            pmsProductCategoryAttributeRelationMapper.deleteByExample(relationExample);
            insertRelationList(id, pmsProductCategoryParam.getProductAttributeIdList());
        } else {
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            pmsProductCategoryAttributeRelationMapper.deleteByExample(relationExample);
        }
        return pmsProductCategoryMapper.updateByPrimaryKeySelective(pmsProductCategory);
    }

    @Override
    public int delete(Long id) {
        return pmsProductCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param id
     * @param productAttributeIdList
     */
    private void insertRelationList(Long id, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(id);
            relationList.add(relation);
        }
        pmsProductCategoryAttributeRelationDao.insertList(relationList);
    }

    /**
     * 没有父分类时设置为一级分类，根据分类的parentId设置分类的level
     *
     * @param pmsProductCategory
     */
    private void setCategoryLevel(PmsProductCategory pmsProductCategory) {
        //没有父分类时设为一级分类
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = pmsProductCategoryMapper.selectByPrimaryKey(pmsProductCategory.getParentId());
            if (parentCategory != null) {
                pmsProductCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                pmsProductCategory.setLevel(0);
            }
        }
    }
}
