package com.canopus.website.api.interceptor;

import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestRequest;
import com.canopus.website.api.utils.RestServiceMapping;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:30
 * @Description:
 */
public abstract class AbstractRequestInterceptor implements RequestInterceptor{
    public AbstractRequestInterceptor() {
    }

    public boolean execute(RestServiceMapping serviceMapping, RestRequest request, Response response) {
        return this.execute();
    }

    protected abstract boolean execute();

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }

    public String toString() {
        return this.getClass().getName() + "|" + this.getName();
    }
}
