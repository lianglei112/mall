package com.macro.mall.service.impl;


import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.OmsOrderReturnReasonMapper;
import com.macro.mall.model.OmsOrderReturnReason;
import com.macro.mall.model.OmsOrderReturnReasonExample;
import com.macro.mall.service.OmsOrderReturnReasonService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/28 23:13
 * @deprecated 订单退货原因管理service层实现类
 */
@Slf4j
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderReturnReasonServiceImpl.class);

    @Autowired
    private OmsOrderReturnReasonMapper omsOrderReturnReasonMapper;

    @Override
    public int create(OmsOrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        return omsOrderReturnReasonMapper.insert(returnReason);
    }

    @Override
    public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.setOrderByClause("sort desc");
        return omsOrderReturnReasonMapper.selectByExample(example);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return omsOrderReturnReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        return omsOrderReturnReasonMapper.updateByPrimaryKey(returnReason);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return omsOrderReturnReasonMapper.deleteByExample(example);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        if (!status.equals(0) && !status.equals(1)) {
            return 0;
        }
        OmsOrderReturnReason record = new OmsOrderReturnReason();
        record.setStatus(status);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return omsOrderReturnReasonMapper.updateByExampleSelective(record, example);
    }
}
