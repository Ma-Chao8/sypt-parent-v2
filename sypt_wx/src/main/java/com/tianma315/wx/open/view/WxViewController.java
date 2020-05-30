package com.tianma315.wx.open.view;

import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.UserService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/wx")
@Controller
public class WxViewController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    /**
     * 获取jspai签名 然后跳转到获取地理位置和扫一扫页面
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/positionSan")
    public String positionSan(Model model, HttpServletRequest request){
        String url = request.getRequestURL().toString();
        log.info(url);
        WxJsapiSignature wxJsapiSignature;
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            log.error("获取微信签名异常", e);
            model.addAttribute("msg", "获取签名失败,请重试");
            return "error/fail";
        }
        model.addAttribute("wxJsapiSignature", wxJsapiSignature);
        return "wx/position-san";
    }

    @PostMapping("/bindUser")
    public String register(UserDO user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = user.getUsername();
        UserDO result = userService.getUserDOByUserName(userName);
        if (result == null) {
            throw new MessageException("没有此用户");
        }
        //将业务系统的用户与社交用户绑定
        providerSignInUtils.doPostSignUp(userName, new ServletWebRequest(request));
        //跳转到index
        return "redirect:/index";
    }
}
