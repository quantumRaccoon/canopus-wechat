package com.canopus.website.api.interceptor.after;

import com.canopus.website.api.interceptor.AbstractRequestInterceptor;
import com.canopus.website.api.interceptor.AfterInterceptor;
import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.interceptor.InterceptorType;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:05
 * @Description:
 */
public abstract class AbstractAfterInterceptor extends AbstractRequestInterceptor implements AfterInterceptor {
    public AbstractAfterInterceptor() {
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.BIZ;
    }

    public InterceptorType getType() {
        return InterceptorType.AFTER;
    }
}
