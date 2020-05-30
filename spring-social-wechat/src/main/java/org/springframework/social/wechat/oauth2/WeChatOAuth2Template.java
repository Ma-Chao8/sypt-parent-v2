package org.springframework.social.wechat.oauth2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
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
 * @Description: 完成微信的OAuth2认证流程的模板类, 国内厂商实现的OAuth2每个都不同, spring默认提供的OAuth2Template适应不了，只能针对每个厂商自己微调。
 * @author: zcm
 * @date: 2018/3/27
 */
public class WeChatOAuth2Template extends OAuth2Template {
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REDIRECT_URL = "http://caihl.tianma315.com/auth.html";
    private String clientId;
    private String clientSecret;
    private String accessTokenUrl;

    private ObjectMapper objectMapper = new ObjectMapper() {{
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }};


    public WeChatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> parameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        accessTokenRequestUrl.append("?appid=" + clientId);
        accessTokenRequestUrl.append("&secret=" + clientSecret);
        accessTokenRequestUrl.append("&code=" + authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri=" + redirectUri);
        //accessTokenRequestUrl.append("&redirect_uri=" + REDIRECT_URL);
        return getAccessToken(accessTokenRequestUrl);
    }

    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);
        refreshTokenUrl.append("?appid=" + clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token=" + refreshToken);
        return getAccessToken(refreshTokenUrl);
    }


    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {
        logger.info(accessTokenRequestUrl.toString());
        String text = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
        Map<String, Object> response = null;
        try {
            //text = "{\"access_token\":\"24_F4s8o7-Ka-8Iq1s5PSX9jYptoJcFtHs5nf8ERVzDu5hg94IyzGNWhBIkwYat08Xh4vnIS8JDMLCRoOj40cWdw12ge51kZC2sGnyvf9PI80U\", \"expires_in\":\"7200\", \"refresh_token\":\"24_2qcklP4p1AaWvus84mz-ZJwsgpkuEfhAttpHKX3ggIrK-Mv3f-pB8LSaohH5ThiytZI-_sk0Y7xthZ8kb3uu2Wzhh-lQPisBN7ki89sQf_E\", \"openid\":\"oyUbEjuXYE9HxmQLzW1KDN-MQPbA\",\"scope\":\"snsapi_userinfo\"}";
            response = objectMapper.readValue(text, Map.class);
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
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
//        parameters.setRedirectUri(REDIRECT_URL);
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_userinfo";
        String targetUrl = url;
        try {
            targetUrl = URLDecoder.decode(targetUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            targetUrl = url;
        }
        logger.info("{}", targetUrl);
        return targetUrl;
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 微信返回的contentType是html/text，e。
     */
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


}

