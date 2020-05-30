package org.springframework.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.wechat.api.WeChatApi;
import org.springframework.social.wechat.oauth2.WeChatAccessGrant;
import org.springframework.social.wechat.oauth2.WeChatServiceProvider;


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
 * @Description: social链接搭建
 * @author: zcm
 * @date: 2018/3/26
 */
public class WeChatConnectionFactory extends OAuth2ConnectionFactory<WeChatApi> {

    private static  final String DEFAULT_PROVIDER_ID = "wechat";


    public WeChatConnectionFactory(String appId, String appSecret) {
        this(appId, appSecret, (String) null);
    }

//    public WeChatConnectionFactory(String providerId,String appId, String appSecret,String appNamespace) {
//        super(providerId, new WeChatServiceProvider(providerId,appId, appSecret,appNamespace), new WeChatApiAdapter());
//    }

    public WeChatConnectionFactory(String appId, String appSecret, String appNamespace) {
        super(DEFAULT_PROVIDER_ID,
                new WeChatServiceProvider(DEFAULT_PROVIDER_ID,appId, appSecret, appNamespace),
                new WeChatApiAdapter());
    }

    //当前用户信息表未关联用户表,用openid作为用户id
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeChatAccessGrant) {
            WeChatAccessGrant grant = (WeChatAccessGrant) accessGrant;
            return grant.getOpenId();
        }
        return super.extractProviderUserId(accessGrant);
    }

    @Override
    public Connection<WeChatApi> createConnection(AccessGrant accessGrant) {
        ApiAdapter<WeChatApi> apiAdapter = getApiAdapter();
        WeChatApiAdapter weChatApiAdapter = null;
        if (apiAdapter instanceof WeChatApiAdapter) {
            weChatApiAdapter = (WeChatApiAdapter) apiAdapter;
            if (accessGrant instanceof WeChatAccessGrant) {
                weChatApiAdapter.setOpenId(((WeChatAccessGrant) accessGrant).getOpenId());
            }
        }

        return new OAuth2Connection<WeChatApi>(getProviderId(),
                extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),
                weChatApiAdapter);
    }


    @Override
    public Connection<WeChatApi> createConnection(ConnectionData data) {
        ApiAdapter<WeChatApi> apiAdapter = getApiAdapter();
        WeChatApiAdapter weChatApiAdapter = null;
        if (apiAdapter instanceof WeChatApiAdapter) {
            weChatApiAdapter = (WeChatApiAdapter) apiAdapter;
            weChatApiAdapter.setOpenId(data.getProviderUserId());
        }
        return new OAuth2Connection<WeChatApi>(data, getOAuth2ServiceProvider(), weChatApiAdapter);
    }

    private OAuth2ServiceProvider<WeChatApi> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeChatApi>) getServiceProvider();
    }
}
