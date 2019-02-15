package com.canopus.website.api.annoations;

import java.lang.annotation.*;

/**
 * @Author: dai-ych
 * @Date: 2019/1/25 10:53
 * @Description:
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestParam {
    String name();

    boolean required() default true;
}
