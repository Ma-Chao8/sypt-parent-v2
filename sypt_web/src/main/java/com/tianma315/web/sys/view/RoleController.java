package com.tianma315.web.sys.view;

import java.util.Arrays;
import java.util.List;

import com.tianma315.core.sys.service.RoleService;
import com.tianma315.logger.annotation.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianma315.web.base.BaseController;
import com.tianma315.core.base.Result;
import com.tianma315.core.sys.domain.RoleDO;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
    String prefix = "sys/role";
    @Autowired
    RoleService roleService;

    @RequiresPermissions("sys:role:role")
    @GetMapping()
    String role() {
        return prefix + "/role";
    }

    @RequiresPermissions("sys:role:role")
    @GetMapping("/list")
    @ResponseBody()
    List<RoleDO> list() {

        List<RoleDO> roles = roleService.findAll(getCompanyId(),getUserId());
        return roles;
    }
    @GetMapping("/list/{procedureLinkId}")
    @ResponseBody()
    List<RoleDO> dataList(@PathVariable("procedureLinkId")Integer id) {

        List<RoleDO> roles = roleService.findAllByProcedureLinkId(id);
        return roles;
    }

    @Logger("添加角色")
    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    String add() {
        return prefix + "/add";
    }

    @Logger("编辑角色")
    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        RoleDO roleDO = roleService.selectById(id);
        model.addAttribute("role", roleDO);
        return prefix + "/edit";
    }

    @Logger("保存角色")
    @RequiresPermissions("sys:role:add")
    @PostMapping("/save")
    @ResponseBody()
    Result<String> save(RoleDO role) {
        if (getUserId() == 1L){
            role.setCompanyId(0L);
        }else{
            role.setCompanyId(getCompanyId());
        }
        roleService.insert(role);
        return Result.ok();
    }

    @Logger("更新角色")
    @RequiresPermissions("sys:role:edit")
    @PostMapping("/update")
    @ResponseBody()
    Result<String> update(RoleDO role) {
        roleService.updateById(role);
        return Result.ok();
    }

    @Logger("删除角色")
    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    @ResponseBody()
    Result<String> save(Long id) {
        roleService.deleteById(id);
        return Result.ok();
    }

    @RequiresPermissions("sys:role:batchRemove")
    @Logger("批量删除角色")
    @PostMapping("/batchRemove")
    @ResponseBody
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        roleService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
}
