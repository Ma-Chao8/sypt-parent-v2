package com.tianma315.logger;

import com.tianma315.logger.annotation.Logger;
import com.tianma315.logger.model.Log;
import com.tianma315.logger.provider.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
public abstract class LogConfigurationAdapter {

    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(com.tianma315.logger.annotation.Logger)")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    /**
     * <pre>
     * 保存日志
     * </pre>
     * <small> 2018年3月22日 | Aron</small>
     *
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        Logger logger = method.getAnnotation(Logger.class);
        if (logger != null) {
            // 注解上的描述
            log.setOperation(logger.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        log.setParams(args);
        log.setProcessTime(time);
        // 系统当前时间
        Date date = new Date();
        // 保存系统日志
        if (getLogService() != null)
            getLogService().save(log);
    }
}
