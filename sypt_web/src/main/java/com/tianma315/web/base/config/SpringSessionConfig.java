package com.tianma315.web.base.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "ifast", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
