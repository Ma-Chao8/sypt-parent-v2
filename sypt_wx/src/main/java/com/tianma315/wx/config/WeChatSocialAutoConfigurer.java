package com.tianma315.wx.config;

import com.tianma315.wx.social.service.impl.JdbcConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.social.wechat.api.WeChatApi;
import org.springframework.social.wechat.connect.WeChatConnectionFactory;
import org.springframework.social.wechat.connect.WeChatConnectionSignUp;

import javax.sql.DataSource;

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
 * Created by zcm on 2019/8/5.
 */
@Configuration
@EnableSocial
public class WeChatSocialAutoConfigurer extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        String appId = environment.getProperty("spring.social.wechat.app-id");
        String appSecret = environment.getProperty("spring.social.wechat.app-secret");
        configurer.addConnectionFactory(new WeChatConnectionFactory(appId, appSecret));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        return repository;
    }

//    /**
//     * @return
//     */
//    @Bean
//    public ConnectionSignUp connectionSignUp() {
//        return new WeChatConnectionSignUp();
//    }


    /**
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

    /**
     * @param repository
     * @return
     */
    @Bean
    @Scope(value = "Request", proxyMode = ScopedProxyMode.INTERFACES)
    public WeChatApi weChatApi(ConnectionRepository repository) {
        Connection<WeChatApi> connection = repository.findPrimaryConnection(WeChatApi.class);
        return connection != null ? connection.getApi() : null;
    }

    /**
     * 社交登录配类
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        return new SpringSocialConfigurer() {
            @Override
            protected <T> T postProcess(T object) {
                SocialAuthenticationFilter filter = (SocialAuthenticationFilter)
                        super.postProcess(object);
                filter.setFilterProcessesUrl("/wx");
//                filter.setFilterProcessesUrl("/open");
                filter.setSignupUrl("/socialRegister");
                return (T) filter;
            }
        };
    }

}
