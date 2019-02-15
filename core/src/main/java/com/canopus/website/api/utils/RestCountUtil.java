package com.canopus.website.api.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:35
 * @Description:
 */
public final class RestCountUtil {
    private static final ConcurrentMap<String, Set> countKeyMap = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, LongAdder> countMap = new ConcurrentHashMap<>();

    public static void addCountKey(String key, String value) {
        ((Set) countKeyMap.computeIfAbsent(key, (k) -> {
            return new ConcurrentHashSet();
        })).add(value);
    }

    public static void incr(String key) {
        ((LongAdder) countMap.computeIfAbsent(key, (k) -> {
            return new LongAdder();
        })).increment();
    }

    public static ConcurrentMap<String, Set> getCountKeyMap() {
        return countKeyMap;
    }

    public static ConcurrentMap<String, LongAdder> getCountMap() {
        return countMap;
    }

    private RestCountUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
