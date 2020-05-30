package org.springframework.social.wechat.api.impl;

import org.springframework.social.wechat.api.WeChatApi;
import org.springframework.social.wechat.api.model.UserInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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
 * @version 1.0
 * @Description: 微信接口实现类
 * @author: zcm
 * @date: 2018/3/26
 */
public class WeChatApiImpl extends TokenApiImpl implements WeChatApi {
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
    private static final String DEFAULT_LANG = "zh_CN";
    private String lang, providerId, appId, appSecret, accessToken, appNamespace;


    /**
     * @param providerId
     * @param appId
     * @param appSecret
     * @param accessToken
     * @param appNamespace
     */
    public WeChatApiImpl(String providerId, String appId, String appSecret, String accessToken, String appNamespace) {
        this(providerId, appId, appSecret, accessToken, appNamespace, DEFAULT_LANG);
    }

    /**
     * @param providerId
     * @param appId
     * @param appSecret
     * @param accessToken
     * @param appNamespace
     * @param lang
     */
    public WeChatApiImpl(String providerId, String appId, String appSecret, String accessToken, String appNamespace, String lang) {
        this.accessToken = accessToken;
        this.appNamespace = appNamespace;
        this.lang = lang;
        this.appId = appId;
        this.appSecret = appSecret;
        this.providerId = providerId;
    }

    /**
     * @param openId
     * @return
     */
    @Override
    public UserInfo getUserInfo(String openId) {
//        SpringContextHolder.getBean();
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", getAccessToken());
        params.put("openid", openId);
        params.put("lang", "zh_CN");
        String url = buildGetUrl(URL_GET_USER_INFO, params);
        String text = getRestTemplate().getForObject(url, String.class);
        try {
            UserInfo userInfo = getObjectMapper().readValue(text, UserInfo.class);
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private String getAppId() {
        return appId;
    }

    private String getAppSecret() {
        return appSecret;
    }

    private String getProviderId() {
        return providerId;
    }

    private String getAccessToken() {
        return accessToken;
    }




}
