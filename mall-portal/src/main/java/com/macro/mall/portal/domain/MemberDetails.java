package com.macro.mall.portal.domain;

import com.macro.mall.model.UmsMember;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/7/1 14:30
 * @deprecated 会员详情封装
 */
@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class MemberDetails implements UserDetails {

    private UmsMember umsMember;

    public MemberDetails(UmsMember umsMember) {
        this.umsMember = umsMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return umsMember.getPassword();
    }

    @Override
    public String getUsername() {
        return umsMember.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsMember.getStatus() == 1;
    }

    public UmsMember getUmsMember() {
        return umsMember;
    }
}
