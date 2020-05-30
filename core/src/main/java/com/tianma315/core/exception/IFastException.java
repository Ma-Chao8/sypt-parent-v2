package com.tianma315.core.exception;

/**
 * <pre>
 * 应用运行时异常，统一抛此类
 * </pre>
 *
 * <small> 2018年4月6日 | Aron</small>
 */
public class IFastException extends MessageException {

    public IFastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IFastException(String message, Throwable cause) {
        super(message, cause);
    }

    public IFastException(String message) {
        super(message);
    }

    public IFastException(Throwable cause) {
        super(cause);
    }

}
