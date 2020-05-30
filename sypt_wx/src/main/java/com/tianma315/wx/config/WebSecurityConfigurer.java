package com.tianma315.wx.config;

import com.tianma315.wx.security.dao.WxDaoAuthenticationProvider;
import com.tianma315.wx.security.encoding.WxMD5PasswordEncoder;
import com.tianma315.wx.user.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;


@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/open/**", "/trace/view/template/**", "/login/*", "/auth/**","/wx/**", "/socialRegister")
//                .antMatchers("/open/**", "/trace/view/template/**", "/login/*", "/auth/*", "/socialRegister")
//                .antMatchers("/trace/view/template/**", "/login/*", "/auth/**", "/open/**", "/socialRegister")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(springSocialConfigurer)
                .and()
//                .apply(springSocialConfigurer2)
//                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .cors()
                .disable()
        ;

//        http.authorizeRequests().anyRequest().permitAll();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
//        auth.authenticationProvider(authenticationProvider())
//                .userDetailsService(userDetailsService())
//                .passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().mvcMatchers("/**/*.*");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new WxDaoAuthenticationProvider() {{
            setUserDetailsService(userDetailsService());
            setPasswordEncoder(passwordEncoder());
            setSaltSource((user) -> {
                return user.getUsername();
            });
        }};
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new WxMD5PasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}
