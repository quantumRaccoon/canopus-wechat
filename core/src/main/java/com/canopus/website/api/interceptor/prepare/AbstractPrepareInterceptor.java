package com.canopus.website.api.interceptor.prepare;

import com.canopus.website.api.interceptor.AbstractRequestInterceptor;
import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.interceptor.InterceptorType;
import com.canopus.website.api.interceptor.PrepareInterceptor;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:30
 * @Description:
 */
public abstract class AbstractPrepareInterceptor extends AbstractRequestInterceptor implements PrepareInterceptor {
    public AbstractPrepareInterceptor() {
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.BIZ;
    }

    public InterceptorType getType() {
        return InterceptorType.PREPARE;
    }
}
