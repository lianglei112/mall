package com.macro.mall.service;

import com.macro.mall.model.UmsResourceCategory;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 14:07
 * @description 后台资源service层
 */
public interface UmsResourceCategoryService {

    int create(UmsResourceCategory umsResourceCategory);

    int delete(Long id);

    int update(Long id, UmsResourceCategory umsResourceCategory);

    List<UmsResourceCategory> listAll();

}
