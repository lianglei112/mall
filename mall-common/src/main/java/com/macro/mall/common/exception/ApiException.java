package com.macro.mall.common.exception;

import com.macro.mall.common.api.IErrorCode;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/15 22:06
 * @deprecated 自定义异常
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
