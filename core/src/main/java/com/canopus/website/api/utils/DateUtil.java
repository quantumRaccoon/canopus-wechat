package com.canopus.website.api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:34
 * @Description:
 */
public class DateUtil {
    public static String formatNow(String format) {
        LocalDateTime ldt = LocalDateTime.now();
        return ldt.format(DateTimeFormatter.ofPattern(format));
    }

    private DateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
