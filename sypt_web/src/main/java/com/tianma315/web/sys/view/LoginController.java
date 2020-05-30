package com.tianma315.web.sys.view;

import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.domain.MenuDO;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.sys.service.MenuService;
import com.tianma315.core.utils.MD5Utils;
import com.tianma315.core.base.Result;
import com.tianma315.logger.annotation.Logger;
import com.tianma315.web.base.BaseController;
import com.tianma315.web.base.config.IFastConfig;
import com.tianma315.web.base.type.EnumErrorCode;
import com.tianma315.core.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;

    @Autowired
    IFastConfig iFastConfig;


    @Logger("请求访问主页")
    @GetMapping({"/", "/home"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("username", getUser().getUsername());
        FileDO fileDO = fileService.selectById(getUser().getPicId());
        model.addAttribute("picUrl", fileDO == null ? null : fileDO.getUrl());
        return "index_v1";
    }

    @GetMapping("/login")
    ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        String name = iFastConfig.getName();
        modelAndView.addObject("name",name);
        return modelAndView;
    }

    @Logger("登录")
    @PostMapping("/login")
    @ResponseBody
    Result<String> ajaxLogin(String username, String password) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

    @GetMapping("/403")
    String error403() {
        return "403";
    }

}
