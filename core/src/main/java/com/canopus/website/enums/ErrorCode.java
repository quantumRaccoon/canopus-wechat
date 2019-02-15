package com.canopus.website.enums;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 16:23
 * @Description:
 */
public enum ErrorCode {

    /**
     * 严重的权限异常
     */
    PERMISSION_DENY_MISSING_I_TOKEN("100", "请重新登录"),

    /**
     * ====================== 权限验证相关异常 1XX==========================
     */
    // 登陆错误, 登陆导致错误的情况下使用该枚举
    TOKEN_INVALID_ERROR("100", "iToken无效, 请重新登录"),

    ACCOUNT_UNAVAILABLE("103", "帐号停用, 请联系管理员"),

    NO_PERMISSION_ERROR("109", "没有权限"),

    /**
     * ======================通信异常错误 4XX============================
     */
    INVOKE_DUBBO_ERROR("400", "dubbo调用失败"),
    INVOKE_SMS_VERIFY_RESULT_ERROR("401", "验证码验证失败"),

    /**
     * 自定义错误
     */
    USER_DEFINED_ERROR("1000", ""),

    /**
     * 无效id
     */
    ID_INVALID("901", "无效id"),

    /**
     * 远程调用错误
     */
    RPC_ERROR("991", "RPC错误"),

    /**
     * 内部严重错误
     */
    UNEXPECTED_INTERNAL_ERROR("999", "内部错误"),

    /**
     * 参数校验不全
     */
    PARAMETER_MISSING("3001", "缺少参数或参数为空"),

    BUSINESS_DATA_ILLEGAL("3002", "业务数据逻辑错误"),

    /**
     * 参数校验不全
     */
    PARAMETER_VALUE_OUT_OF_RANGE("3002", "参数不在取值范围内"),

    /**
     * 主从表关系错误
     */
    ID_MISMATCH("902", "无效id"),

    /**
     * 数据库数据错误
     */
    SYSTEM_DATA_ERROR("600", "系统数据错误"),
    DUPLICATE_KEY_VIOLATION_ERROR("601", "不能新增重复数据"),
    DATA_STATUS_ERROR("602", "数据状态异常"),
    DATA_EXISTENCE_ERROR("603", "数据已存在"),
    CONSTRAINT_VIOLATION_ERROR("604", "违反数据约束错误"),
    /**
     * 参数校验不通过 ，未明确具体参数的情况使用该枚举
     */
    PARAMETER_VALID_NOT_PASS("PARAMETER_VALID_NOT_PASS", "参数校验不通过"),

    /**
     * 参数id 已经存在
     */
    PARAM_ID_ALREADY_EXISTS("PARAM_ID_ALREADY_EXISTS EXISTS", "参数id已经存在"),

    /**
     * 参数不存在
     */
    PARAM_NOT_EXISTS("PARAM_NOT_EXISTS", "参数不存在"),

    /**
     * 参数版本不一致
     */
    PARAM_VERSION_DIFFERENT("PARAM_VERSION_DIFFERENT", "参数版本不一致"),

    /**
     * 参数使用中，删除失败
     */
    PARAM_IN_USING("PARAM_IN_USING", "参数使用中，删除失败"),

    /**
     * 日期格式错误
     */
    WRONG_DATE_PATTERN("WRONG_DATE_PATTERN", "日期格式错误"),

    /**
     * 数量超出最大值
     */
    OVERFLOW_MAX_VALUE("OVERFLOW_MAX_VALUE", "数量超出最大值"),
    /**
     * id缺失
     */
    ID_MISSING("ID_MISSING", "id缺失"),
    /**
     * 无效token
     */
    TOKEN_INVALID("TOKEN_INVALID", "无效token"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    ;

    /**
     * 枚举代码
     */
    private final String code;
    /**
     * 枚举信息
     */
    private final String message;

    /**
     * 私有构造方法
     *
     * @param code    错误简码
     * @param message 错误信息描述
     */
    ErrorCode(String code, String message) {

        this.code = code;
        this.message = message;
    }

    /**
     * 根据代码获取枚举
     */
    public static ErrorCode getEnumByCode(String code) {

        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode().equalsIgnoreCase(code)) {
                return errorCode;
            }
        }
        return ErrorCode.SYSTEM_ERROR;
    }

    public String getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }

    @Override
    public String toString() {

        return "ErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
