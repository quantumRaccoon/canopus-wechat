package com.canopus.website.api.utils;

import java.util.UUID;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:00
 * @Description:
 */
public final class ShortUUID {
    public static String fromString(String name) {
        return toShortString(UUID.fromString(name));
    }

    public static String nameUUIDFromBytes(byte[] bytes) {
        return toShortString(UUID.nameUUIDFromBytes(bytes));
    }

    public static String randomUUID() {
        return toShortString(UUID.randomUUID());
    }

    private static String toShortString(UUID u) {
        long mostSigBits = u.getMostSignificantBits();
        long leastSigBits = u.getLeastSignificantBits();
        return digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4) + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(leastSigBits, 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << digits * 4;
        return Long.toString(hi | val & hi - 1L, 36).substring(1);
    }

    private ShortUUID() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
