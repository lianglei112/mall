package com.macro.mall.common.api;

/**
 * @auther lianglei
 * @description 枚举了一些常用API操作码
 * @date 2023/6/13
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    /**
     * 返回状态码
     */
    private long code;

    /**
     * 返回信息
     */
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
