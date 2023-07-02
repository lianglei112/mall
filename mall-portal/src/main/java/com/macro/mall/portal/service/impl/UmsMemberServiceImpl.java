package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.MemberDetails;
import com.macro.mall.portal.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/1 14:23
 * @deprecated 会员管理service层实现类
 */
@Slf4j
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    private UmsMemberMapper umsMemberMapper;


    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }
}
