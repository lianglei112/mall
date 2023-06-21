package com.macro.mall.service;

import com.macro.mall.dto.UmsMenuNode;
import com.macro.mall.model.UmsMenu;

import java.util.List;

public interface UmsMenuService {

    int create(UmsMenu umsMenu);

    int update(Long id, UmsMenu umsMenu);

    UmsMenu getItem(Long id);

    int delete(Long id);

    int updateHidden(Long id, Integer hidden);

    List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize);

    List<UmsMenuNode> treeList();
}
