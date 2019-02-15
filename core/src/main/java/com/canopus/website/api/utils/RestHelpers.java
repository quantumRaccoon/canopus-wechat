package com.canopus.website.api.utils;

import com.canopus.website.api.config.RestConfiguration;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.interceptor.AfterInterceptor;
import com.canopus.website.api.model.Request;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestError;
import com.canopus.website.api.model.RestRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:00
 * @Description:
 */
@Slf4j
public final class RestHelpers {
    public static void printJsonForJsonp(HttpServletResponse response, Request request, Response restResponse) {
        try {
            response.setContentType("text/javascript; charset=UTF-8");
            PrintWriter out = response.getWriter();
            String jsonString = JsonUtil.toJSONString(restResponse, true);
            if (StringUtils.isNotBlank(request.getCallback())) {
                out.print(request.getCallback() + "(" + jsonString + ")");
            } else {
                out.print(jsonString);
            }

        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static Response returnResponse(RestRequest request, Response response, RestServiceMapping serviceMapping, RestConfiguration configuration) {
        String cmd = request.getRequest().getCmd();
        List<AfterInterceptor> afterInterceptors = configuration.getAfterInterceptors();

        AfterInterceptor requestInterceptor;
        String requestInterceptorName;
        for(Iterator var6 = afterInterceptors.iterator(); var6.hasNext(); log.debug("REST_AFTER_INTERCEPTOR_COMPLETE， cmd: {}, name: {}", cmd, requestInterceptorName + "|" + requestInterceptor.getClass().getName())) {
            requestInterceptor = (AfterInterceptor)var6.next();
            requestInterceptorName = requestInterceptor.getName();
            log.debug("REST_AFTER_INTERCEPTOR_START, cmd: {}, name: {}", cmd, requestInterceptorName + "|" + requestInterceptor.getClass().getName());

            try {
                requestInterceptor.execute(serviceMapping, request, response);
            } catch (Exception var10) {
                response.setException(var10);
                log.warn("执行拦截器 {} 失败", requestInterceptorName, var10);
            }
        }

        return response;
    }

    public static void handleException(Request request, Response response, Throwable ex) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        rootCause = rootCause == null ? ex : rootCause;
        log.error("捕获到Rest异常：request={}", request, rootCause);
        RestError restError;
        if (rootCause instanceof ServiceException) {
            ServiceException se = (ServiceException)rootCause;
            restError = new RestError(se.getErrorCode(), se.getErrorInfo(), se.getDesc());
        } else {
            restError = new RestError("1000", "服务器开小差了~~~", (Object)null);
            if (request.isDebug()) {
                String stackTrace = ExceptionUtils.getStackTrace(rootCause);
                stackTrace = StringUtils.substringBefore(stackTrace, "com.isesol.arch.api.RestServiceController");
                response.setDebugInfo(stackTrace);
            }

            recordToErrorCounter(request.getCmd());
        }

        response.setStatusCode(500);
        response.setError(restError);
        response.setResponseTime(new Date());
    }

    protected static void recordToErrorCounter(String cmd) {
        String today = DateUtil.formatNow("yyyyMMdd");
        RestCountUtil.incr("api:error:" + today);
        RestCountUtil.incr("api:error:" + today + ":cmd:" + cmd);
    }

    private RestHelpers() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
