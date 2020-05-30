package com.tianma315.wx.handler;

import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.exception.MessageViewException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 */
@ControllerAdvice
public class ApplicationExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MessageException.class)
    Result messageException(MessageException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getMessage(), null);
    }

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(MessageViewException.class)
    ModelAndView messageException(MessageViewException e) {
        log.error(e.getMessage(), e);
        ModelAndView view = new ModelAndView("message");
        view.addObject("message", e.getMessage());
        return view;
    }


    /**
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.fail(error.getDefaultMessage(), null);
    }


    /**
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {BindException.class})
    Result bindException(BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.fail(error.getDefaultMessage(), null);
    }

    /**
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        e.printStackTrace();
        log.error(e.getMessage(), e);
        return Result.fail("系统繁忙", null);
    }
}
