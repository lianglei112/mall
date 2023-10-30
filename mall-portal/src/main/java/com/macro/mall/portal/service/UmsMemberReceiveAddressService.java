package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/7 22:06
 * @deprecated 用户地址管理service层
 */
public interface UmsMemberReceiveAddressService {

    List<UmsMemberReceiveAddress> list();

    UmsMemberReceiveAddress getItem(Long memberReceiveAddressId);

}
