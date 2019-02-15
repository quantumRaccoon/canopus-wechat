package com.canopus.website.api.config;

import com.canopus.website.api.interceptor.after.AlarmInterceptor;
import com.canopus.website.api.interceptor.after.ResponseLogInterceptor;
import com.canopus.website.api.interceptor.after.ResponseTimeInfoInterceptor;
import com.canopus.website.api.interceptor.prepare.AuthInterceptor;
import com.canopus.website.api.interceptor.prepare.CountInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:27
 * @Description:
 */
@Slf4j
public class RestConfigurationImpl extends RestConfiguration{
    public RestConfigurationImpl() {
    }

    protected void doInit() {
        this.prepareInterceptors.add(new CountInterceptor());
        this.prepareInterceptors.add(new AuthInterceptor());
        this.prepareInterceptors.addAll(this.customPrepareInterceptors);
        this.afterInterceptors.add(new ResponseLogInterceptor());
        this.afterInterceptors.add(new ResponseTimeInfoInterceptor());
        this.afterInterceptors.add(new AlarmInterceptor());
        this.afterInterceptors.addAll(this.customAfterInterceptors);
    }
}
