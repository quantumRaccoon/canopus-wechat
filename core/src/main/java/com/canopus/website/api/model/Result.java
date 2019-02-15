package com.canopus.website.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:57
 * @Description:
 */
@Data
public class Result<T> implements Serializable {
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String desc;
    private transient T model;
}
