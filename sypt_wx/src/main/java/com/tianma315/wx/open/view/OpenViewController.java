package com.tianma315.wx.open.view;

import com.tianma315.core.exception.MessageException;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.wx.common.BaseController;
import com.tianma315.wx.common.model.WxConfig;
import com.tianma315.wx.common.service.WxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/8/19.
 */
@Controller
public class OpenViewController extends BaseController {
    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    @Autowired
    private WxService wxService;

    /**
     * 溯源界面
     *
     * @param code
     * @param request
     * @return
     */
    @GetMapping("/open/{code}")
    void query(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
        WxUserInfoDO wxUserInfo;
        try {
//            wxUserInfo = getWxUser();
            sendRedirect(request, response, code);
        } catch (MessageException e) {
            doAuthentication(request, response, null, code);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new MessageException("网页跳转失败", e);
        }
    }

    /**
     * @param code
     * @param request
     * @return
     */
    @GetMapping("/open/trace/{code}")
    ModelAndView callback(@PathVariable String code, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("trace");
        //获取微信配置信息
//        WxConfig config = wxService.getWxConfig(request.getRequestURL().toString());
        view.addObject("code", code);
//        view.addObject("wxConfig", config);
        return view;
    }

    /**
     * @param ident
     * @param code
     * @param request
     * @param response
     */
    @GetMapping("/open/{ident}/{code}")
    void query(@PathVariable String ident, @PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
        try {
            //getWxUser();
            sendRedirectByIdent(request, response, ident, code);
        } catch (MessageException e) {
            doAuthentication(request, response, ident, code);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new MessageException("网页跳转失败", e);
        }
    }


    /**
     * @param code
     * @param request
     * @return
     */
    @GetMapping("/open/trace/{ident}/{code}")
    ModelAndView callback(@PathVariable String ident, @PathVariable String code, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("trace");
        //获取微信配置信息
//        WxConfig config = wxService.getWxConfig(request.getRequestURL().toString());
        view.addObject("code", code);
        view.addObject("ident", ident);
//        view.addObject("wxConfig", config);
        return view;
    }

    /**
     * 注册界面
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/socialRegister")
    public ModelAndView register(Map<String, String> map) {
        return new ModelAndView("register", map);
    }

    /**
     * 替换url中的占位符
     *
     * @param url
     * @param params
     * @return
     */
    protected String replaceUrl(String url, Map<String, Object> params) {
        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.get(key) != null ? String.valueOf(params.get(key)) : "";
                url = url.replace(key, value);
            }
        }

        return url;
    }

    /**
     * 生成state随机字符串
     *
     * @return
     */
    protected String generateState() {
        String str = "ABCDEFGHHIJKLMNOPKRSTUVWXYZabcdefghhijklmnopkrstuvwxyz0123456789";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 12; i++) {
            int index = (int) (Math.random() * str.length());
            buffer.append(str.charAt(index));
        }
        return buffer.toString();
    }

    /**
     * 构建回调路径
     *
     * @param request
     * @param returnToUrlParameters
     * @return
     */
    protected String buildReturnToUrl(HttpServletRequest request, Set<String> returnToUrlParameters) {
        StringBuffer sb = request.getRequestURL();
        sb.append("?");
        for (String name : returnToUrlParameters) {
            // Assume for simplicity that there is only one value
            String value = request.getParameter(name);
            if (value == null) {
                continue;
            }
            sb.append(name).append("=").append(value).append("&");
        }
        sb.setLength(sb.length() - 1); // strip trailing ? or &
        return sb.toString();
    }

    /**
     * @param request
     * @return
     */
    protected boolean detectRejection(HttpServletRequest request) {
        Set<?> parameterKeys = request.getParameterMap().keySet();
//        log.info("request [{}] {}", request.getRequestURL(), parameterKeys);
        if ((parameterKeys.size() == 1) && (parameterKeys.contains("state"))) {
            return false;
        }
        return /*parameterKeys.size() > 0
                &&*/ !parameterKeys.contains("oauth_token")
                && !parameterKeys.contains("code")
                && !parameterKeys.contains("scope");
    }

    /**
     * @param request
     * @param response
     * @param ident
     * @param code
     */
    void doAuthentication(HttpServletRequest request, HttpServletResponse response, String ident, String code) {
        try {
            if (detectRejection(request)) {
                Map<String, Object> params = new HashMap<>();
                params.put("APPID", wxService.getAppId());
                params.put("SCOPE", "snsapi_userinfo");

                if (StringUtils.isBlank(wxService.getRedirectUrl())) {//
                    params.put("REDIRECT_URI", buildReturnToUrl(request, new HashSet<>()));
                } else {
                    String[] urls = wxService.getRedirectUrl().split(",");
                    String redirectUrl;
                    if (StringUtils.isBlank(ident)) {
                        redirectUrl = urls[0].replace("{CODE}", code);
                        params.put("REDIRECT_URI", redirectUrl);
                    } else {
                        redirectUrl = urls[1].replace("{CODE}", code).replace("{IDENT}", ident);
                        params.put("REDIRECT_URI", redirectUrl);
                    }
                }

                params.put("STATE", generateState());
                redirectStrategy.sendRedirect(request, response, replaceUrl(AUTHORIZE_URL, params));
            } else {
                WxUserInfoDO wxUserInfo = wxService.getWxUserInfo(request.getParameter("code"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(wxUserInfo.getOpenid(), wxUserInfo, new ArrayList<>());
                authentication.setDetails(wxUserInfo);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if (StringUtils.isNotBlank(ident)) {
                    sendRedirectByIdent(request, response, ident, code);
                } else {
                    sendRedirect(request, response, code);
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new MessageException("授权跳转失败", ie);
        }
    }

    /**
     * @param request
     * @param response
     * @param code
     */
    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, String code) throws IOException {
        redirectStrategy.sendRedirect(request, response, String.format("/open/trace/%s", code));
    }

    /**
     * @param request
     * @param response
     * @param ident
     * @param code
     */
    private void sendRedirectByIdent(HttpServletRequest request, HttpServletResponse response, String ident, String code) throws IOException {
        redirectStrategy.sendRedirect(request, response, String.format("/open/trace/%s/%s", ident, code));
    }
}
