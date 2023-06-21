package com.macro.mall.service;

import com.macro.mall.model.UmsResource;

import java.util.List;

/**
 * @version 1.0
 * @Author lianglei
 * @Date: 2023/06/20/ 11:08
 * @description 资源管理service层
 */
public interface UmsResourceService {

    int create(UmsResource umsResource);

    int update(Long id, UmsResource umsResource);

    int delete(Long id);

    UmsResource getItem(Long id);

    List<UmsResource> listAll();

    List<UmsResource> list(Long categoryId, String nameKeyWord, String urlKeyword, Integer pageNum, Integer pageSize);

}
