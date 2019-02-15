package com.canopus.website.api.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 11:07
 * @Description:
 */
@JsonFormat(
        shape = JsonFormat.Shape.OBJECT
)
public enum RestErrorCode {
    CMD_NOT_FOUND("-1", "请求的服务不存在"),
    CMD_NULL("0", "请求的命令不能为空"),
    SYSTEM_ERROR("1", "系统错误"),
    PARAM_ERROR("2", "参数错误"),
    RESPONSE_TIMEOUT("3", "响应超时"),
    DENY("4", "拒绝访问"),
    NEED_TOKEN("14", "请登陆"),
    TOKEN_INVALID("15", "请重新登陆");

    private final String code;
    private final String message;

    private RestErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RestErrorCode getEnumByCode(String code) {
        RestErrorCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RestErrorCode errorCode = var1[var3];
            if (code.equalsIgnoreCase(errorCode.getCode())) {
                return errorCode;
            }
        }

        return SYSTEM_ERROR;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "RestErrorCode(code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }
}
