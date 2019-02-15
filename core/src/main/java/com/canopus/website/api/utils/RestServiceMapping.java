package com.canopus.website.api.utils;

import com.canopus.website.api.annoations.Authority;
import com.canopus.website.api.annoations.ResultType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:19
 * @Description:
 */
@Data
public class RestServiceMapping {
    private String name;
    private String description;
    private Class clazz;
    private Object bean;
    private String beanName;
    private String methodName;
    private Method method;
    private String url;
    private String superUrl;
    private Class<?>[] parameterTypes;
    private Map<Integer, RestAnnotationParameter> annotationParameters = new LinkedHashMap<>();
    private Authority authority;
    private Class requestType;
    private int retryTimes;
    private int timeout;
    private boolean logResponse;
    private boolean logRequest;
    private ResultType resultType;

    public String getCmd() {
        return StringUtils.isNotBlank(this.superUrl) ? this.superUrl + "/" + this.url : this.url;
    }
}
