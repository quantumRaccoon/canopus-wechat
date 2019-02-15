package com.canopus.website.api.interceptor;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:24
 * @Description:
 */
public interface RequestInterceptor {
    String getName();

    InterceptorType getType();

    InterceptorPurpose getInterceptorPurpose();
}
