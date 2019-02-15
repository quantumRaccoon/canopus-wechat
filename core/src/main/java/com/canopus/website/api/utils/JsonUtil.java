package com.canopus.website.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:01
 * @Description:
 */
public final class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper prettyObjectMapper = new ObjectMapper();

    public static String toJSONString(Object obj) {
        return toJSONString(obj, false);
    }

    public static String toJSONString(Object obj, boolean pretty) {
        try {
            return pretty ? prettyObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        objectMapper.registerModule(new JodaModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.setDefaultPrettyPrinter(new MinimalPrettyPrinter());
        prettyObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        prettyObjectMapper.registerModule(new JodaModule());
        prettyObjectMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
    }
}
