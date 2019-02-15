package com.canopus.website.api.interceptor;

import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestRequest;
import com.canopus.website.api.utils.RestServiceMapping;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:23
 * @Description:
 */
public interface AfterInterceptor extends RequestInterceptor{
    boolean execute(RestServiceMapping restServiceMapping, RestRequest restRequest, Response response);
}
