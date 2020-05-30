package org.springframework.social.wechat.api.impl;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;

/**
 * Description
 * <p>
 * Created by zcm on 2019/8/8.
 */
public abstract class TokenApiImpl extends AbstractOAuth2ApiBinding {
    protected Logger logger;

    public TokenApiImpl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     *
     * @return
     */
    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper() {{
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }};
    }


    @Override
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = super.getRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }
}
