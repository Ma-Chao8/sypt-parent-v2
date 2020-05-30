package com.tianma315.web.base.handler;

import com.tianma315.core.base.Result;
import com.tianma315.core.exception.IFastException;
import com.tianma315.core.exception.MessageException;
import com.tianma315.web.base.type.EnumErrorCode;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(IFastException.class)
    public Result<String> handleBDException(IFastException e) {
        try {
            int code = Integer.parseInt(e.getMessage());
            return Result.build(code, EnumErrorCode.getMsgByCode(code));
        } catch (NumberFormatException e1) {
            log.info("错误码使用错误，异常内容请抛出EnumErrorCode类的枚举值");
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(MessageException.class)
    Result messageException(MessageException e) {
        log.error(e.getMessage(),e);
        return Result.fail(e.getMessage(), null);
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(),e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.fail(error.getDefaultMessage(),null);
    }


    @ExceptionHandler(value = {BindException.class})
    Result bindException(BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.fail(error.getDefaultMessage(), null);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.duplicateKeyExist.getCode(), EnumErrorCode.duplicateKeyExist.getMsg());
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.pageNotFound.getCode(), EnumErrorCode.pageNotFound.getMsg());
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result<String> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.notAuthorization.getCode(), EnumErrorCode.notAuthorization.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
    }
}
