package com.tianma315.web;

import com.tianma315.core.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * <pre>
 * ifast 入口
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@SpringBootApplication(scanBasePackages = {"com.tianma315"})
@EnableTransactionManagement
public class WebApplication extends SpringBootServletInitializer {
    // 启动的时候要注意，由于我们在controller中注入了RestTemplate，所以启动的时候需要实例化该类的一个实例
    @Autowired
    private RestTemplateBuilder builder;

    // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

    /**
     * <pre>
     * </pre>
     * <small> 2018年3月22日 | Aron</small>
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
        System.out.println("==================> run at http://localhost:" + serverProperties.getPort() + serverProperties.getContextPath() + "  <==================");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}