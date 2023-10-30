package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dao.OmsOrderDao;
import com.macro.mall.dao.OmsOrderOperateHistoryDao;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.OmsOrderMapper;
import com.macro.mall.mapper.OmsOrderOperateHistoryMapper;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.OmsOrderExample;
import com.macro.mall.model.OmsOrderOperateHistory;
import com.macro.mall.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/27 21:32
 * @deprecated 订单管理service层实现类
 */
@Slf4j
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderServiceImpl.class);

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @Autowired
    private OmsOrderDao omsOrderDao;

    @Autowired
    private OmsOrderOperateHistoryMapper omsOrderOperateHistoryMapper;

    @Autowired
    private OmsOrderOperateHistoryDao omsOrderOperateHistoryDao;

    @Override
    public List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return omsOrderDao.getList(queryParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = omsOrderDao.delivery(deliveryParamList);
        //添加操作记录
        List<OmsOrderOperateHistory> collect = deliveryParamList.stream()
                .map(
                        omsOrderDeliveryParam -> {
                            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                            history.setOrderId(omsOrderDeliveryParam.getOrderId());
                            history.setCreateTime(new Date());
                            history.setOperateMan("后台管理员");
                            history.setOrderStatus(2);
                            history.setNote("完成发货");
                            return history;
                        }
                ).collect(Collectors.toList());
        omsOrderOperateHistoryDao.insertList(collect);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int close(List<Long> ids, String note) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setStatus(4);
        omsOrder.setNote(note);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = omsOrderMapper.updateByExample(omsOrder, example);
        List<OmsOrderOperateHistory> collect = ids.stream()
                .map(orderId -> {
                    OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                    history.setOrderId(orderId);
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(4);
                    history.setNote("订单关闭：" + note);
                    return history;
                }).collect(Collectors.toList());
        omsOrderOperateHistoryDao.insertList(collect);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setDeleteStatus(1);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        return omsOrderMapper.updateByExample(omsOrder, example);
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        return omsOrderDao.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setModifyTime(new Date());
        int count = omsOrderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        omsOrderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int updateMoneyInfo(OmsMoneyInfoParam omsMoneyInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(omsMoneyInfoParam.getOrderId());
        order.setFreightAmount(omsMoneyInfoParam.getFreightAmount());
        order.setDiscountAmount(omsMoneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        omsOrderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(omsMoneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(omsMoneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        omsOrderOperateHistoryMapper.insert(history);
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = omsOrderMapper.updateByPrimaryKeySelective(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息："+note);
        omsOrderOperateHistoryMapper.insert(history);
        return count;
    }
}
