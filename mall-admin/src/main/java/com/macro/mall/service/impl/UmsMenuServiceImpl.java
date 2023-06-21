package com.macro.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.controller.UmsMenuController;
import com.macro.mall.dto.UmsMenuNode;
import com.macro.mall.mapper.UmsMenuMapper;
import com.macro.mall.model.UmsMenu;
import com.macro.mall.model.UmsMenuExample;
import com.macro.mall.service.UmsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/19 22:41
 */
@Slf4j
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMenuServiceImpl.class);

    @Autowired
    private UmsMenuMapper umsMenuMapper;

    @Override
    public int create(UmsMenu umsMenu) {
        return umsMenuMapper.insert(umsMenu);
    }

    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return umsMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return umsMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return umsMenuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuExample example = new UmsMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return umsMenuMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> umsMenuList = umsMenuMapper.selectByExample(new UmsMenuExample());
        List<UmsMenuNode> result = null;
        if (CollectionUtil.isNotEmpty(umsMenuList)) {
            result = umsMenuList.stream()
                    .filter(menu -> menu.getParentId().equals(0L))
                    .map(menu -> covertMenuNode(menu, umsMenuList))
                    .collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 将UmsMenu转换为UmsMenuNode并设置children属性
     *
     * @param menu
     * @param umsMenuList
     * @return
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> umsMenuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        //进行递归调用查询数据
       List<UmsMenuNode> children =  umsMenuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, umsMenuList))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 修改菜单层级
     *
     * @param umsMenu
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //沒有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = umsMenuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }

    }
}
