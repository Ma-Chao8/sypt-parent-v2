package com.tianma315.web.base.config;

import com.tianma315.logger.LogConfigurationAdapter;
import com.tianma315.logger.provider.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * 日志切面
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Configuration
public class LogAspect extends LogConfigurationAdapter {

    @Autowired
    private LogService logService;

    @Override
    public LogService getLogService() {
        return logService;
    }
}
