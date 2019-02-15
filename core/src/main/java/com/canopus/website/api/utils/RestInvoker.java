package com.canopus.website.api.utils;

import com.canopus.website.api.model.RestRequest;
import lombok.Data;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:59
 * @Description:
 */
@Data
public class RestInvoker {
    private String cmd;
    private RestServiceMapping serviceMapping;
    private RestRequest restRequest;
    private Class<?>[] methodParameterClasses = null;
    private Object[] methodParameters = null;

    public RestInvoker(String cmd, RestServiceMapping serviceMapping, RestRequest restRequest, Class<?>[] methodParameterClasses, Object[] methodParameters) {
        this.cmd = cmd;
        this.serviceMapping = serviceMapping;
        this.restRequest = restRequest;
        this.methodParameterClasses = methodParameterClasses;
        this.methodParameters = methodParameters;
    }

}
