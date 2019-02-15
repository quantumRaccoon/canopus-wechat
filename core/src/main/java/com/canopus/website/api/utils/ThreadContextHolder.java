package com.canopus.website.api.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:32
 * @Description:
 */
@Slf4j
public final class ThreadContextHolder {
    private static ThreadLocal<Map<String, Object>> attrsThreadLocalHolder = new ThreadLocal<>();

    public static void setAttr(String key, Object object) {
        getAttrs().put(key, object);
    }

    public static Object getAttr(String key) {
        return getAttrs().get(key);
    }

    private static Map<String, Object> getAttrs() {
        Map<String, Object> attrs = (Map<String, Object>)attrsThreadLocalHolder.get();
        if (attrs == null) {
            attrs = new HashMap<>();
        }

        attrsThreadLocalHolder.set(attrs);
        return (Map<String, Object>)attrs;
    }

    public static void cleanAttrs() {
        attrsThreadLocalHolder.set(new HashMap<>());
    }

    public static void removeAttr(String key) {
        getAttrs().remove(key);
    }

    private ThreadContextHolder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
