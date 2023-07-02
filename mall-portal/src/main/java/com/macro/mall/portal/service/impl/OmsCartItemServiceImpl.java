package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.UmsMemberService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/29 21:30
 * @deprecated 购物车管理service层实现类
 */
@Slf4j
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsCartItemServiceImpl.class);

    @Autowired
    private OmsCartItemMapper omsCartItemMapper;

    @Autowired
    private UmsMemberService umsMemberService;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int add(OmsCartItem omsCartItem) {
        int count = 0;
        UmsMember currentMember = umsMemberService.getCurrentMember();
        omsCartItem.setMemberId(currentMember.getId());
        omsCartItem.setMemberNickname(currentMember.getNickname());
        omsCartItem.setDeleteStatus(0);
        OmsCartItem exitCartItem = getCartItem(omsCartItem);
        //查询是否存在购物车，不存在则执行插入操作，否则执行更新操作
        if (exitCartItem == null) {
            omsCartItem.setCreateDate(new Date());
            count = omsCartItemMapper.insert(omsCartItem);
        } else {
            exitCartItem.setModifyDate(new Date());
            //设置购买的数量
            exitCartItem.setQuantity(omsCartItem.getQuantity() + omsCartItem.getQuantity());
            count = omsCartItemMapper.updateByPrimaryKeySelective(exitCartItem);
        }
        return count;
    }

    /**
     * 查询该用户是否存在购物车
     *
     * @param omsCartItem
     * @return
     */
    private OmsCartItem getCartItem(OmsCartItem omsCartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(omsCartItem.getMemberId())
                .andProductIdEqualTo(omsCartItem.getProductId())
                .andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(omsCartItem.getProductSkuId())) {
            criteria.andProductSkuIdEqualTo(omsCartItem.getProductSkuId());
        }
        //查询购物车列表
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectByExample(example);
        if (!Collections.isEmpty(omsCartItems)) {
            return omsCartItems.get(0);
        }
        return null;
    }
}
