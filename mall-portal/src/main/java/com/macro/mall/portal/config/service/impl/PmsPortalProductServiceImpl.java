package com.macro.mall.portal.config.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.PmsProductCategoryMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductCategory;
import com.macro.mall.model.PmsProductCategoryExample;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.portal.config.domain.PmsProductCategoryNode;
import com.macro.mall.portal.config.service.PmsPortalProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;


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
                .map(itemChildren -> covert(itemChildren,pmsProductCategoryList))
                .collect(Collectors.toList());
        pmsProductCategoryNode.setChildren(children);
        return pmsProductCategoryNode;
    }
}
