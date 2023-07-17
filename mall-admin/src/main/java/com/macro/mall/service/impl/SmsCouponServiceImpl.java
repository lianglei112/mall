package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.SmsCouponDao;
import com.macro.mall.dao.SmsCouponProductCategoryRelationDao;
import com.macro.mall.dao.SmsCouponProductRelationDao;
import com.macro.mall.dto.SmsCouponParam;
import com.macro.mall.mapper.SmsCouponMapper;
import com.macro.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.macro.mall.mapper.SmsCouponProductRelationMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.SmsCouponService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:44
 * @deprecated 优惠券管理Service层实现类
 */
@Slf4j
@Service
public class SmsCouponServiceImpl implements SmsCouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCouponServiceImpl.class);

    @Autowired
    private SmsCouponMapper smsCouponMapper;

    @Autowired
    private SmsCouponDao smsCouponDao;

    @Autowired
    private SmsCouponProductRelationMapper smsCouponProductRelationMapper;

    @Autowired
    private SmsCouponProductRelationDao smsCouponProductRelationDao;

    @Autowired
    private SmsCouponProductCategoryRelationMapper smsCouponProductCategoryRelationMapper;

    @Autowired
    private SmsCouponProductCategoryRelationDao smsCouponProductCategoryRelationDao;


    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int create(SmsCouponParam smsCouponParam) {
        smsCouponParam.setCount(smsCouponParam.getPublishCount());
        smsCouponParam.setUseCount(0);
        smsCouponParam.setReceiveCount(0);
        //插入优惠券表
        int count = smsCouponMapper.insert(smsCouponParam);
        //插入优惠券与商品关系表
        if (smsCouponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : smsCouponParam.getProductRelationList()) {
                productRelation.setCouponId(smsCouponParam.getId());
            }
            smsCouponProductRelationDao.insertList(smsCouponParam.getProductRelationList());
        }
        //插入优惠券和商品分类关系表
        if (smsCouponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : smsCouponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setId(smsCouponParam.getId());
            }
            smsCouponProductCategoryRelationDao.insertList(smsCouponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        //删除优惠券
        int count = smsCouponMapper.deleteByPrimaryKey(id);
        //删除商品关联
        deleteProductRelation(id);
        //删除商品分类关联
        deleteProductCategoryRelation(id);
        return count;
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return smsCouponDao.getItem(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int update(Long id, SmsCouponParam couponParam) {
        couponParam.setId(id);
        int count = smsCouponMapper.updateByPrimaryKeySelective(couponParam);
        //删除后插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
            }
            deleteProductRelation(id);
            smsCouponProductRelationDao.insertList(couponParam.getProductRelationList());
        }
        //删除后插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            deleteProductCategoryRelation(id);
            smsCouponProductCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        PageHelper.startPage(pageNum, pageSize);
        return smsCouponMapper.selectByExample(example);
    }

    /**
     * 删除商品分类关联
     *
     * @param id
     */
    private void deleteProductCategoryRelation(Long id) {
        SmsCouponProductCategoryRelationExample example = new SmsCouponProductCategoryRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        smsCouponProductCategoryRelationMapper.deleteByExample(example);
    }

    /**
     * 删除商品关联
     *
     * @param id
     */
    private void deleteProductRelation(Long id) {
        SmsCouponProductRelationExample example = new SmsCouponProductRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        smsCouponProductRelationMapper.deleteByExample(example);

    }


}
