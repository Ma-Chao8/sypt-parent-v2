package com.tianma315.web.sys.view;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.sys.domain.*;
import com.tianma315.core.sys.domain.vo.UserVo;
import com.tianma315.core.sys.service.DictService;
import com.tianma315.core.sys.service.RoleService;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.utils.MD5Utils;
import com.tianma315.core.base.Result;
import com.tianma315.logger.annotation.Logger;
import com.tianma315.web.base.BaseController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 *
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
    private String prefix = "sys/user";
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    DictService dictService;

    /**
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param userDTO
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<Page<UserDO>> list(Integer pageNumber, Integer pageSize, UserDO userDTO) {
        // 查询列表数据
        Page<UserDO> page = new Page<>(pageNumber, pageSize);
        Wrapper<UserDO> wrapper = new EntityWrapper<UserDO>(userDTO);
        if (getUserId() == 1L){
            //wrapper.eq("company_id",0L);
        }else {
            wrapper.eq("company_id",getCompanyId()).and().ne("id",1L).ne("dept_id",0);
        }

        page = userService.selectPage(page, wrapper);
        int total = userService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
    }

    /**
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:add")
    @Logger("添加用户")
    @GetMapping("/add")
    String add(Model model) {
        Wrapper wrapper = new EntityWrapper();
        if (getUserId() == 1L){
            wrapper.eq("company_id",0);
        }else {
            wrapper.eq("company_id",getCompanyId());
        }
        List<RoleDO> roles = roleService.selectList(wrapper);
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    /**
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @Logger("编辑用户")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.selectById(id);
        model.addAttribute("user", userDO);

        List<RoleDO> roles = roleService.findListByUserId(id,getCompanyId(),getUserId());
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    /**
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:add")
    @Logger("保存用户")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(UserDO user) {
        user.setCompanyId(getCompanyId());
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        userService.insert(user);
        return Result.ok();
    }

    /**
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @Logger("更新用户")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(UserDO user) {
        userService.updateById(user);
        return Result.ok();
    }

    /**
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @Logger("更新用户")
    @PostMapping("/updatePeronal")
    @ResponseBody
    Result<String> updatePeronal(UserDO user) {
        userService.updatePersonal(user);
        return Result.ok();
    }

    /**
     * @param id
     * @return
     */
    @RequiresPermissions("sys:user:remove")
    @Logger("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    Result<String> remove(Long id) {
        userService.deleteById(id);
        return Result.ok();
    }

    /**
     * @param userIds
     * @return
     */
    @RequiresPermissions("sys:user:batchRemove")
    @Logger("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    Result<String> batchRemove(@RequestParam("ids[]") Long[] userIds) {
        userService.deleteBatchIds(Arrays.asList(userIds));
        return Result.ok();
    }

    /**
     * @param params
     * @return
     */
    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    /**
     * @param userId
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:resetPwd")
    @Logger("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {

        UserDO userDO = new UserDO();
        userDO.setId(userId);
        model.addAttribute("user", userDO);
        return prefix + "/reset_pwd";
    }

    /**
     * @param userVO
     * @return
     */
    @Logger("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    Result<String> resetPwd(UserVo userVO) {
        userService.resetPwd(userVO, getUser());
        return Result.ok();
    }

    /**
     * @param userVO
     * @return
     */
    @RequiresPermissions("sys:user:resetPwd")
    @Logger("admin提交更改用户密码")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    Result<String> adminResetPwd(UserVo userVO) {
        userService.adminResetPwd(userVO);
        return Result.ok();

    }

    /**
     * @return
     */
    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = userService.getTree();
        return tree;
    }

    /**
     * @return
     */
    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }

    /**
     * @param model
     * @return
     */
    @GetMapping("/personal")
    String personal(Model model) {
        UserDO userDO = userService.selectById(getUserId());
        model.addAttribute("user", userDO);
        List<DictDO> hobbyList = dictService.getHobbyList(userDO);
        model.addAttribute("hobbyList", hobbyList);
        List<DictDO> sexList = dictService.getSexList();
        model.addAttribute("sexList", sexList);
        return prefix + "/personal";
    }

    /**
     * @param file
     * @param avatar_data
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/uploadImg")
    Result<?> uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = userService.updatePersonalImg(file, avatar_data, getUserId());
        return Result.ok(result);
    }
}
