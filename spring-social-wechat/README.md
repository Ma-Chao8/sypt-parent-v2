# 微信登陆

## spring-social-wechat微信登陆使用教程

* 在配置文件中配置微信的相关信息
```
spring.social.wechat.app-id= your appid
spring.social.wechat.app-secret=your app secret
```
* 创建一个新的配置类继承WeChatSocialConfigurerAdapter类并在该类上添加@Configuration和@EnableSocial两个注解
```
@Configuration
@EnableSocial
public class WeChatSocialConfigurer extends WeChatSocialConfigurerAdapter {
    ...
}
```
如此，整个微信登陆的配置就完成了。  
当你访问 /auth/wechat接口就跳转到微信，使用微信登陆