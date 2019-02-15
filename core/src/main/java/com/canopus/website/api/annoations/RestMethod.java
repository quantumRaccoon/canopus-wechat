package com.canopus.website.api.annoations;

import com.canopus.website.api.model.DynamicRestRequest;
import com.canopus.website.api.model.RestRequest;

import java.lang.annotation.*;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 11:19
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestMethod {
    String cgiServiceName() default "";

    String url() default "";

    String name() default "";

    String description() default "";

    Class<? extends RestRequest> requestType() default DynamicRestRequest.class;

    Authority authority() default Authority.UNKNOW;

    int retryTimes() default 0;

    int timeout() default 0;

    boolean logRequest() default true;

    boolean logResponse() default true;

    ResultType resultType() default ResultType.DEFAULT;
}
