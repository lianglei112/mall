package com.macro.mall.service;

import com.macro.mall.model.SmsHomeAdvertise;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/16 22:33
 * @deprecated 首页广告管理Service层
 */
public interface SmsHomeAdvertiseService {

    int create(SmsHomeAdvertise advertise);

    int delete(List<Long> ids);

    int updateStatus(Long id, Integer status);

    SmsHomeAdvertise getItem(Long id);

    int update(Long id, SmsHomeAdvertise advertise);

    List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);

}
