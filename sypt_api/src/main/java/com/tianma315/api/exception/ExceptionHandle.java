package com.tianma315.api.exception;


import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandle {
//    @Autowired
//    public ExceptionHandle(Result result) {
//        this.result = result;
//    }

    // 捕捉shiro的异常
    @ExceptionHandler(AuthorizationException.class)
    public Result handle402() {
        return Result.fail("您没有权限访问！");
    }

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public Result handle401() {
        return Result.fail("认证错误！");
    }


    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        return Result.fail("访问出错，无法访问: " + ex.getMessage());
    }

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(MessageException.class)
    Result messageException(MessageException e) {
        return Result.fail(e.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
