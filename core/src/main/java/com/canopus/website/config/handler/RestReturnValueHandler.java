package com.canopus.website.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 18:18
 * @Description: REST类型的返回值处理器
 * implements HandlerMethodReturnValueHandler
 */
@Component
public class RestReturnValueHandler {

//    private static final String STATUS_CODE_SUCCEEDED = "200";
//    private static final String STATUS_CODE_INTERNAL_ERROR = "500";
//
//    @Autowired
//    private MessageConverter messageConverter;
//
//    @Override
//    public boolean supportsReturnType(MethodParameter returnType) {
//        if (returnType.hasMethodAnnotation(ResponseBody.class)
//                || (!returnType.getDeclaringClass().equals(ModelAndView.class))
//                && returnType.getMethod().getDeclaringClass().isAnnotationPresent(RestController.class)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest) throws Exception {
//        mavContainer.setRequestHandled(true);
//
//        Map<String, Object> resultMap = new HashMap<>();
//
//        resultMap.put("statusCode", STATUS_CODE_SUCCEEDED);
//        resultMap.put("data", returnValue);
//
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//
//        messageConverter.write(resultMap, MediaType.APPLICATION_JSON_UTF8, new ServletServerHttpResponse(response));
//    }
}
