package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMember;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/1 14:22
 * @deprecated 会员管理service层
 */
public interface UmsMemberService {

    /**
     * 获取当前登录会员
     *
     * @return
     */
    UmsMember getCurrentMember();


    void updateIntegration(Long id, int integration);
}
