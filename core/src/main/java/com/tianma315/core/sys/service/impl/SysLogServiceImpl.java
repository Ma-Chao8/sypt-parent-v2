package com.tianma315.core.sys.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.sys.dao.LogMapper;
import com.tianma315.core.sys.domain.LogDO;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.SysLogService;
import com.tianma315.core.utils.HttpContextUtils;
import com.tianma315.core.utils.IPUtils;
import com.tianma315.core.utils.JSONUtils;
import com.tianma315.core.utils.ShiroUtils;
import com.tianma315.logger.model.Log;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Service
public class SysLogServiceImpl extends CoreServiceImpl<LogMapper, LogDO> implements SysLogService {

    @Override
    public void save(Log log) {
        LogDO sysLog = new LogDO();
        sysLog.setOperation(log.getOperation());
        sysLog.setMethod(log.getMethod());
        try {
            String params = JSONUtils.beanToJson(log.getParams());
            sysLog.setParams(params);  // 请求的参数
        } catch (Throwable e) {

        }

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getUser();
        if (null == currUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId(-1L);
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUserId(-1L);
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUserId(ShiroUtils.getUserId());
            sysLog.setUsername(ShiroUtils.getUser().getUsername());
        }
        sysLog.setTime((int) log.getProcessTime());
        // 系统当前时间
        sysLog.setGmtCreate(new Date());
        // 保存系统日志
        insert(sysLog);
    }
}
