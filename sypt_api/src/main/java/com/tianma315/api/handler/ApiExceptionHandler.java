package com.tianma315.api.handler;

import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统自定义消息异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MessageException.class)
    public Result messageException(MessageException e) {
        e.printStackTrace();
        logger.error(e.getMessage(), e);
        return Result.fail(e.getMessage(), null);
    }


    /**
     * 未知异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public Result throwable(Throwable e) {
        e.printStackTrace();
        logger.error(e.getMessage(), e);
        return Result.fail("系统繁忙", null);
    }

}
