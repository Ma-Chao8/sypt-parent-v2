package org.springframework.social.wechat.oauth2;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.wechat.api.WeChatApi;
import org.springframework.social.wechat.api.impl.WeChatApiImpl;


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
 * @Description: 微信服务提供
 * @author: zcm
 * @date: 2018/3/27
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChatApi> {

    private final String providerId;
    private final String appId;
    private final String appSecret;
    private String appNamespace;

    /**
     * 微信授权码
     */
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 微信token
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeChatServiceProvider(String providerId, String appId, String appSecret, String appNamespace) {
        super(new WeChatOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appNamespace = appNamespace;
        this.providerId = providerId;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    private String getProviderId() {
        return providerId;
    }

    private String getAppId() {
        return appId;
    }

    private String getAppSecret() {
        return appSecret;
    }

    private String getAppNamespace() {
        return appNamespace;
    }

    /**
     * @param accessToken
     * @return
     */
    @Override
    public WeChatApi getApi(String accessToken) {
        return new WeChatApiImpl(getProviderId(), getAppId(), getAppSecret(), accessToken, getAppNamespace());
    }




}
