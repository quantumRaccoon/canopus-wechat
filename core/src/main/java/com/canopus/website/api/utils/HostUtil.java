package com.canopus.website.api.utils;

import com.google.common.base.Throwables;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 13:46
 * @Description:
 */
public final class HostUtil {
    private static final String SERVER_IP = initIp();
    private static final String HOST_NAME = initHostName();
    private static final String[] IP_HEADER_CANDIDATES = new String[]{"X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    private static String initHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    public static String getHostName() {
        return HOST_NAME;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String[] var1 = IP_HEADER_CANDIDATES;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String header = var1[var3];
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    private static String initIp() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while(e.hasMoreElements()) {
                NetworkInterface x = (NetworkInterface)e.nextElement();
                Enumeration inetAddresses = x.getInetAddresses();

                while(inetAddresses.hasMoreElements()) {
                    String hostAddress = ((InetAddress)inetAddresses.nextElement()).getHostAddress();
                    if (!hostAddress.equals("127.0.0.1")) {
                        String[] split = hostAddress.split("\\.");
                        if (split.length == 4) {
                            return hostAddress;
                        }
                    }
                }
            }

            return null;
        } catch (Throwable throwable) {
            throw Throwables.propagate(throwable);
        }
    }

    public static String getServerIp() {
        return SERVER_IP;
    }

    private HostUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
