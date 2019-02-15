package com.canopus.website.api.interceptor;

import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestRequest;
import com.canopus.website.api.utils.RestServiceMapping;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:28
 * @Description:
 */
public interface PrepareInterceptor extends RequestInterceptor {
    boolean execute(RestServiceMapping restServiceMapping, RestRequest restRequest, Response response);
}
