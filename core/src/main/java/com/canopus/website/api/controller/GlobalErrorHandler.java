package com.canopus.website.api.controller;

import com.canopus.website.api.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 10:45
 * @Description:
 */
@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {
    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_INFO = "errorInfo";
    private static final String DESC_INFO = "desc";

    public GlobalErrorHandler() {
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseBody
    public Map<String,Object> defaultErrorHandler(Exception e) {
        Map<String,Object> msg = Maps.newHashMapWithExpectedSize(2);
        Map<String,Object> error = Maps.newHashMapWithExpectedSize(1);
        if (e instanceof HttpMessageNotReadableException) {
            error.put(ERROR_CODE, "500");
            error.put(ERROR_INFO, "请求内容为空");
        } else if (e instanceof ServiceException) {
            ServiceException se = (ServiceException)e;
            error.put(ERROR_CODE, se.getErrorCode());
            error.put(ERROR_INFO, se.getErrorInfo());
            error.put(DESC_INFO, se.getDesc());
        } else {
            error.put(ERROR_CODE, "500");
            error.put(ERROR_INFO, e.getMessage());
        }

        msg.put("error", error);
        log.error("global error", e);
        return msg;
    }
}
