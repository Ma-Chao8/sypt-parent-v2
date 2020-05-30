package com.tianma315;

import com.tianma315.core.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@MapperScan(basePackages = {"com.tianma315.core.**.dao"})
@EnableTransactionManagement
public class SyptApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SyptApiApplication.class, args);
		ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

}
