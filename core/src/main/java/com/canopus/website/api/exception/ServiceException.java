package com.canopus.website.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 11:03
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException{
    private final String errorCode;
    private final String errorInfo;
    private final String desc;

    public ServiceException(String errorInfo) {
        super(errorInfo);
        this.errorCode = "1000";
        this.errorInfo = errorInfo;
        this.desc = null;
    }

    public ServiceException(String errorCode, String errorInfo) {
        super(errorInfo);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.desc = null;
    }

    public ServiceException(String errorCode, String errorInfo, String desc) {
        super(errorInfo);
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.desc = desc;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.errorCode = "1000";
        this.errorInfo = cause.getMessage();
        this.desc = null;
    }

    public ServiceException(String errorInfo, Throwable cause) {
        super(errorInfo, cause);
        this.errorCode = "1000";
        this.errorInfo = errorInfo;
        this.desc = null;
    }
}
