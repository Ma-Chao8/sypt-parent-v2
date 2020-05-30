package com.tianma315.web.sys.view;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.service.MenuService;
import com.tianma315.core.base.Result;
import com.tianma315.logger.annotation.Logger;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
    String prefix = "sys/menu";
    @Autowired
    MenuService menuService;

    @RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model) {
        return prefix + "/menu";
    }

    @RequiresPermissions("sys:menu:menu")
    @RequestMapping("/list")
    @ResponseBody
    List<MenuDO> list() {
        Wrapper<MenuDO> wrapper = new EntityWrapper();
        wrapper.ne("type",3);

        List<MenuDO> menus = menuService.selectList(wrapper);
        return menus;
    }

    @Logger("添加菜单")
    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pId}")
    String add(Model model, @PathVariable("pId") Long pId) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        return prefix + "/add";
    }

    @Logger("编辑菜单")
    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        MenuDO mdo = menuService.selectById(id);
        Long pId = mdo.getParentId();
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        model.addAttribute("menu", mdo);
        return prefix + "/edit";
    }

    @Logger("保存菜单")
    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(MenuDO menu) {
        menuService.insert(menu);
        return Result.ok();
    }

    @Logger("更新菜单")
    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(MenuDO menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    @Logger("删除菜单")
    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    Result<String> remove(Long id) {
        menuService.deleteById(id);
        return Result.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    Tree<MenuDO> tree() {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTreeByUserId(getUserId());
        return tree;
    }

    @GetMapping("/tree/{roleId}")
    @ResponseBody
    Tree<MenuDO> tree(@PathVariable("roleId") Long roleId) {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTree(roleId,getUserId());
        return tree;
    }
}
