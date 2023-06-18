package com.macro.mall.service;

import com.macro.mall.dto.UmsAdminParam;
import com.macro.mall.model.UmsAdmin;

import java.util.List;

public interface UmsAdminService {

    UmsAdmin register(UmsAdminParam umsAdminParam);

    List<UmsAdmin> list(String keyword, Integer pageNum, Integer pageSize);

    UmsAdmin getItem(Long id);

    int update(Long id, UmsAdmin admin);

    int delete(Long id);
}
