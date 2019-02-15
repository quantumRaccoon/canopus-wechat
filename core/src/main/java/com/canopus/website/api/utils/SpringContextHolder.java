package com.canopus.website.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:42
 * @Description:
 */
@Slf4j
@Service
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    public SpringContextHolder() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        if (null != applicationContext) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为{}", applicationContext);
        }
        applicationContext = applicationContext;
    }

    public void destroy() throws Exception {
        clear();
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static void clear() {
        log.debug("清除SpringContextHolder中的ApplicationContext");
        applicationContext = null;
    }

    private static void assertContextInjected() {
        if (null == applicationContext) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

}
