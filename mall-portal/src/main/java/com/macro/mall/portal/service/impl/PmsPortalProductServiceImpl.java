package com.macro.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;
import com.macro.mall.portal.service.PmsPortalProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/26/ 15:10
 * @description 前台商品管理service实现类
 */
@Slf4j
@Service
public class PmsPortalProductServiceImpl implements PmsPortalProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsPortalProductServiceImpl.class);

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;

    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;

    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;

    @Autowired
    private PmsProductFullReductionMapper pmsProductFullReductionMapper;


    @Override
    public List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        criteria.andPublishStatusEqualTo(1);
        if (StrUtil.isNotBlank(keyword)) {
            criteria.andNameEqualTo("%" + keyword + "%");
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        //1->按照新品   2->按销量   3->价格从低到高   4->价格从高到低
        if (sort == 1) {
            example.setOrderByClause("id desc");
        } else if (sort == 2) {
            example.setOrderByClause("sale desc");
        } else if (sort == 3) {
            example.setOrderByClause("price asc");
        } else if (sort == 4) {
            example.setOrderByClause("price desc");
        }
        return pmsProductMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategoryNode> categoryTreeList() {
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        List<PmsProductCategory> pmsProductCategoryList = pmsProductCategoryMapper.selectByExample(example);
        List<PmsProductCategoryNode> result = null;
        if (!CollectionUtils.isEmpty(pmsProductCategoryList)) {
            result = pmsProductCategoryList.stream()
                    .filter(item -> item.getParentId().equals(0L))
                    .map(item -> covert(item, pmsProductCategoryList))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public PmsPortalProductDetail detail(Long id) {
        PmsPortalProductDetail result = new PmsPortalProductDetail();
        //获取商品信息
        PmsProduct product = pmsProductMapper.selectByPrimaryKey(id);
        result.setProduct(product);
        //获取品牌信息
        PmsBrand brand = pmsBrandMapper.selectByPrimaryKey(product.getBrandId());
        result.setBrand(brand);
        //获取商品属性信息
        PmsProductAttributeExample attributeExample = new PmsProductAttributeExample();
        attributeExample.createCriteria().andProductAttributeCategoryIdEqualTo(product.getProductAttributeCategoryId());
        List<PmsProductAttribute> productAttributeList = pmsProductAttributeMapper.selectByExample(attributeExample);
        result.setProductAttributeList(productAttributeList);
        //获取商品属性值信息
        if (!CollectionUtils.isEmpty(productAttributeList)) {
            List<Long> attributeIds = productAttributeList.stream().map(PmsProductAttribute::getId).collect(Collectors.toList());
            PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
            attributeValueExample.createCriteria().andProductIdEqualTo(product.getId())
                    .andProductAttributeIdIn(attributeIds);
            List<PmsProductAttributeValue> productAttributeValueList = pmsProductAttributeValueMapper.selectByExample(attributeValueExample);
            result.setProductAttributeValueList(productAttributeValueList);
        }
        //获取商品sku库存信息
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(product.getId());
        List<PmsSkuStock> pmsSkuStocks = pmsSkuStockMapper.selectByExample(skuStockExample);
        result.setSkuStockList(pmsSkuStocks);
        //商品阶梯价格设置
        if (product.getPromotionType() == 3) {
            PmsProductLadderExample ladderExample = new PmsProductLadderExample();
            ladderExample.createCriteria().andProductIdEqualTo(product.getId());
            List<PmsProductLadder> pmsProductLadderList = pmsProductLadderMapper.selectByExample(ladderExample);
            result.setProductLadderList(pmsProductLadderList);
        }
        //商品满减价格设置
        if (product.getPromotionType() == 4) {
            PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
            fullReductionExample.createCriteria().andProductIdEqualTo(product.getId());
            List<PmsProductFullReduction> fullReductionList = pmsProductFullReductionMapper.selectByExample(fullReductionExample);
            result.setProductFullReductionList(fullReductionList);
        }
//         result.setCouponList(portalProductDao.getAvailableCouponList(product.getId(),product.getProductCategoryId()));
        return result;
    }

    /**
     * 转换为树节点
     *
     * @param item
     * @param pmsProductCategoryList
     * @return
     */
    private PmsProductCategoryNode covert(PmsProductCategory item, List<PmsProductCategory> pmsProductCategoryList) {
        PmsProductCategoryNode pmsProductCategoryNode = new PmsProductCategoryNode();
        BeanUtils.copyProperties(item, pmsProductCategoryNode);
        List<PmsProductCategoryNode> children = pmsProductCategoryList.stream()
                .filter(itemChildren -> itemChildren.getParentId().equals(item.getId()))
                .map(itemChildren -> covert(itemChildren, pmsProductCategoryList))
                .collect(Collectors.toList());
        pmsProductCategoryNode.setChildren(children);
        return pmsProductCategoryNode;
    }
}
