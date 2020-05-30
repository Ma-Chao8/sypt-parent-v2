package com.tianma315.core.sys.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.MenuMapper;
import com.tianma315.core.sys.dao.RoleMenuMapper;
import com.tianma315.core.sys.dao.UserMapper;
import com.tianma315.core.sys.dao.UserRoleMapper;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.service.MenuService;
import com.tianma315.core.utils.BuildTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class MenuServiceImpl extends CoreServiceImpl<MenuMapper, MenuDO> implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * @param
     * @return 树形菜单
     */

    @Override
    public Tree<MenuDO> getSysMenuTree(Long id) {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<MenuDO> getTreeByUserId(Long userId) {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs;
        if (userId == 1L){
            menuDOs = baseMapper.selectList(null);
        }else {
            menuDOs = roleMenuMapper.selectByUserId(userId);
        }

        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<MenuDO> getTree(Long id,Long userId) {
        // 根据roleId查询权限
       // List<MenuDO> menus = roleMenuMapper.selectByRoleId(id);
        List<MenuDO> menus = baseMapper.selectList(null);
        List<Long> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
        List<Long> temp = menuIds;
        for (MenuDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs;
        if (userId == 1L){
            menuDOs = baseMapper.selectList(null);
        }else {
            menuDOs = roleMenuMapper.selectByUserId(userId);
        }

        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = sysMenuDO.getId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<MenuDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = baseMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
    @Override
    public Set<String> listRoles(Long userId) {
        List<String> perms = baseMapper.listUserRoles(userId);
        Set<String> permsSet = new HashSet<>();
//        for (String perm : perms) {
//            if (StringUtils.isNotBlank(perm)) {
//                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
//            }
//        }
        permsSet.addAll(perms);
        return permsSet;
    }

    @Override
    public List<MenuDO> getMenuByUsername(String username) {
        List<MenuDO> menuDOList = baseMapper.selectMenuByUsername(username);
        return menuDOList;
    }

    @Override
    public List<Tree<MenuDO>> listMenuTree(Long id) {
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        List<MenuDO> menuDOs = baseMapper.listMenuByUserId(id);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<MenuDO>> list = BuildTree.buildList(trees, "0");
        return list;
    }

}
