package com.macro.mall.common.exception;

import com.macro.mall.common.api.IErrorCode;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/15 22:34
 * @deprecated 断言处理类，用于抛出各种API异常
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
