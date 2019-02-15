package com.canopus.website.api.model;

import lombok.Data;

import java.beans.ConstructorProperties;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 13:42
 * @Description:
 */
@Data
public class RestError {
    private String errorCode;
    private Object errorInfo;
    private Object desc;

    @ConstructorProperties({"errorCode", "errorInfo", "desc"})
    public RestError(String errorCode, Object errorInfo, Object desc) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.desc = desc;
    }
}
