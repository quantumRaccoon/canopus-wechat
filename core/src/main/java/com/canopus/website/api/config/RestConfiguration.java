package com.canopus.website.api.config;

import com.canopus.website.api.interceptor.AfterInterceptor;
import com.canopus.website.api.interceptor.PrepareInterceptor;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 10:33
 * @Description:
 */
@Data
public abstract class RestConfiguration {
    String cgiServiceName;
    List<PrepareInterceptor> customPrepareInterceptors = Lists.newArrayList();
    List<AfterInterceptor> customAfterInterceptors = Lists.newArrayList();
    List<PrepareInterceptor> prepareInterceptors = Lists.newArrayList();
    List<AfterInterceptor> afterInterceptors = Lists.newArrayList();

    public void init() {
        this.doInit();
    }
    protected abstract void doInit();
}
