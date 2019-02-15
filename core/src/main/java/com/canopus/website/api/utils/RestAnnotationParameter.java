package com.canopus.website.api.utils;

import lombok.Data;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:21
 * @Description:
 */
@Data
public class RestAnnotationParameter {
    private String name;
    private Class<?> type;
    private boolean required;
}
