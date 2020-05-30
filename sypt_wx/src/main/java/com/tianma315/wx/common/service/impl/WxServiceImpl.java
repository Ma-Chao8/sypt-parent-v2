package com.tianma315.wx.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.wxuserinfo.dao.WxUserInfoDao;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.wx.common.model.WxConfig;
import com.tianma315.wx.common.service.WxService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.wechat.api.model.Ticket;
import org.springframework.social.wechat.api.model.Token;
import org.springframework.social.wechat.oauth2.WeChatAccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
 * Created by zcm on 2019/8/10.
 */
@Service
@Transactional
public class WxServiceImpl implements WxService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String URL_GET_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final String URL_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=LANG";
    private static final String KEY_CACHE_TICKET = "cache_ticket";
    private static final String KEY_CACHE_TOKEN = "cache_token";

    @Value("${spring.social.wechat.app-id}")
    private String appId;
    @Value("${spring.social.wechat.app-secret}")
    private String appSecret;
    @Value("${spring.social.wechat.redirect-url:}")
    private String redirectUrl;

    @Autowired
    private WxUserInfoDao wxUserInfoDao;


    @Override
    public String getAppId() {
        return appId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String getAppSecret() {
        return appSecret;
    }

    protected WeChatAccessGrant getAccessToken(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("APPID", getAppId());
        params.put("SECRET", getAppSecret());
        params.put("CODE", code);
        String text = getRestTemplate().getForObject(replaceUrl(URL_ACCESS_TOKEN, params).toString(), String.class);
        Map<String, Object> response = null;
        try {
            //text = "{\"access_token\":\"24_F4s8o7-Ka-8Iq1s5PSX9jYptoJcFtHs5nf8ERVzDu5hg94IyzGNWhBIkwYat08Xh4vnIS8JDMLCRoOj40cWdw12ge51kZC2sGnyvf9PI80U\", \"expires_in\":\"7200\", \"refresh_token\":\"24_2qcklP4p1AaWvus84mz-ZJwsgpkuEfhAttpHKX3ggIrK-Mv3f-pB8LSaohH5ThiytZI-_sk0Y7xthZ8kb3uu2Wzhh-lQPisBN7ki89sQf_E\", \"openid\":\"oyUbEjuXYE9HxmQLzW1KDN-MQPbA\",\"scope\":\"snsapi_userinfo\"}";
            response = getObjectMapper().readValue(text, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        logger.info("{}", response);

        //返回错误码时直接返回空
        if (StringUtils.isNotBlank(MapUtils.getString(response, "errcode"))) {
            String errcode = MapUtils.getString(response, "errcode");
            String errmsg = MapUtils.getString(response, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:" + errcode + ", errmsg:" + errmsg);
        }
        WeChatAccessGrant accessToken = new WeChatAccessGrant(
                MapUtils.getString(response, "access_token"),
                MapUtils.getString(response, "scope"),
                MapUtils.getString(response, "refresh_token"),
                MapUtils.getLong(response, "expires_in"));
        accessToken.setOpenId(MapUtils.getString(response, "openid"));
        return accessToken;
    }

    /**
     * @param openId
     * @param token
     * @return
     */
    protected WxUserInfoDO getUserInfo(String openId, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("ACCESS_TOKEN", token);
        params.put("OPENID", openId);
        params.put("LANG", "zh_CN");
        String text = getRestTemplate().getForObject(replaceUrl(URL_USER_INFO, params), String.class);
        try {
            WxUserInfoDO userInfo = getObjectMapper().readValue(text, WxUserInfoDO.class);
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public WxConfig getWxConfig(String url) {
        Ticket ticket = null;
        try {
            ticket = getTicket();
        } catch (MessageException e) {
            throw e;
        } catch (Exception e) {
            throw new MessageException("微信Ticket 读取失败", e);
        }

        WxConfig config = new WxConfig(url, ticket.getTicket());
        config.setAppId(appId);
        config.setJsApiList(Arrays.asList("getLocation"));
        return config;
    }

    @Override
    public WxUserInfoDO getWxUserInfo(String code) {
        WeChatAccessGrant accessGrant = getAccessToken(code);
        if (accessGrant == null) {
            throw new MessageException("token获取失败");
        }
        WxUserInfoDO userInfo = getUserInfo(accessGrant.getOpenId(), accessGrant.getAccessToken());
        if (userInfo == null) {
            throw new MessageException("微信用户信息获取失败");
        }
        Wrapper<WxUserInfoDO> wrapper = new EntityWrapper<>();
        wrapper.eq("openid", userInfo.getOpenid());
        List<WxUserInfoDO> list = wxUserInfoDao.selectList(wrapper);
        if (list != null && !list.isEmpty()) {
            WxUserInfoDO wxUserInfo = list.get(0);
            wxUserInfo.setOpenid(userInfo.getOpenid());
            wxUserInfo.setNickname(userInfo.getNickname());
            wxUserInfo.setCountry(userInfo.getCountry());
            wxUserInfo.setProvince(userInfo.getProvince());
            wxUserInfo.setHeadimgurl(userInfo.getHeadimgurl());
            wxUserInfo.setSex(userInfo.getSex());
            wxUserInfo.setCity(userInfo.getCity());
            wxUserInfoDao.updateById(wxUserInfo);
            return wxUserInfo;
        } else {
            userInfo.setCreatedDate(new Date());
            if (wxUserInfoDao.insert(userInfo) != 1) {
                throw new MessageException("用户信息保存失败");
            }
            return userInfo;
        }
    }


    public Token getToken() throws IOException {
        if (CacheUtils.getCache().get(KEY_CACHE_TOKEN) != null) {
            Token token = (Token) CacheUtils.getCache().get(KEY_CACHE_TOKEN);
            if (!token.hasExpiresIn()) {
                return token;
            }
        }
        String url = buildGetUrl(URL_GET_TOKEN, new HashMap<String, Object>() {{
            put("grant_type", "client_credential");
            put("appid", appId);
            put("secret", appSecret);
        }});
        String text = getRestTemplate().getForObject(url, String.class);
        logger.info("{}",text);
        Token token = getObjectMapper().readValue(text, Token.class);
        logger.info("{}",token);
        if (!token.isSuccess()) {
            throw new MessageException(String.format("Token获取失败：%s", token.getErrmsg()));
        }
        CacheUtils.getCache().put(KEY_CACHE_TOKEN, token);
        return token;
    }


    public Ticket getTicket() throws IOException {
        if (CacheUtils.getCache().get(KEY_CACHE_TICKET) != null) {
            Ticket ticket = (Ticket) CacheUtils.getCache().get(KEY_CACHE_TICKET);
            if (!ticket.hasExpiresIn()) {
                return ticket;
            }
        }
        String url = buildGetUrl(URL_GET_TICKET, new HashMap<String, Object>() {{
            put("access_token", getToken().getAccess_token());
            put("type", "jsapi");
        }});

        String text = getRestTemplate().getForObject(url, String.class);
        logger.info("{}",text);
        Ticket ticket = getObjectMapper().readValue(text, Ticket.class);
        logger.info("{}",ticket);
        if (!ticket.isSuccess()) {
            throw new MessageException(String.format("Ticket获取失败：%s", ticket.getErrmsg()));
        }
        CacheUtils.getCache().put(KEY_CACHE_TICKET, ticket);
        return ticket;
    }

    /**
     *
     */
    static class CacheUtils {
        private static Map<String, Object> cache = new ConcurrentHashMap<>();

        /**
         * @return
         */
        public static Map<String, Object> getCache() {
            return cache;
        }
    }

    /**
     * @param url
     * @param params
     * @return
     */
    private String buildGetUrl(String url, Map<String, Object> params) {
        StringBuffer stringBuffer = new StringBuffer(url);
        if (params != null && !params.isEmpty()) {
            if (!url.contains("?")) {
                stringBuffer.append("?");
            } else {
                stringBuffer.append("&");
            }
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                stringBuffer.append(String.format("%s=%s", key, params.get(key)));
                if (keys.hasNext()) {
                    stringBuffer.append("&");
                }
            }
        }
        return stringBuffer.toString();
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
     * @return
     */
    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper() {{
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }};
    }


    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
