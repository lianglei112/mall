package com.macro.mall.common.api;

/**
 * @auther lianglei
 * @description 封装API的错误码
 * @date 2023/6/13
 */
public interface IErrorCode {

    /**
     * 错误状态码
     *
     * @return
     */
    long getCode();

    /**
     * 错误返回信息
     *
     * @return
     */
    String getMessage();
}
