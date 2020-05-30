package org.springframework.social.wechat.config;

import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.wechat.connect.WeChatConnectionFactory;


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
 * 微信登陆配置类
 * <p>
 * Created by zcm on 2018/5/14.
 */
//@EnableSocial
public abstract class WeChatSocialConfigurerAdapter implements SocialConfigurer {


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new WeChatConnectionFactory(env.getProperty("spring.social.wechat.app-id"),
                env.getProperty("spring.social.wechat.app-secret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        //
        return new AuthenticationNameUserIdSource();
    }

}