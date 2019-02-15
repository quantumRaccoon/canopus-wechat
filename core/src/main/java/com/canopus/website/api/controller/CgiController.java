package com.canopus.website.api.controller;

import com.alibaba.fastjson.JSON;
import com.canopus.website.api.annoations.ResultType;
import com.canopus.website.api.config.RestConfiguration;
import com.canopus.website.api.constants.RestErrorCode;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.interceptor.AfterInterceptor;
import com.canopus.website.api.interceptor.PrepareInterceptor;
import com.canopus.website.api.model.*;
import com.canopus.website.api.utils.*;
import com.canopus.website.api.utils.HostUtil;
import com.canopus.website.api.utils.JsonUtil;
import com.canopus.website.api.utils.RestHelpers;
import com.canopus.website.api.utils.RestServiceMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 13:26
 * @Description:
 */
@Slf4j
//@RestController
//@RequestMapping({"/cgi"})
public class CgiController {
//    private static final SimpleTimeLimiter limiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());
//    @Resource
//    private RestConfiguration configuration;
//    private static final String PARAMETERS = "parameters";
//    private static final String PASSWORD = "password";
//
//    public CgiController() {
//    }
//
//    @RequestMapping(
//            method = {RequestMethod.GET}
//    )
//    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode objectNode = mapper.createObjectNode();
//        ObjectNode parameters = mapper.createObjectNode();
//        Request restRequest = null;
//
//        Response restResponse;
//        try {
//            Map<String, String[]> parameterMap = request.getParameterMap();
//            Iterator var17 = parameterMap.entrySet().iterator();
//
//            while(true) {
//                while(var17.hasNext()) {
//                    Map.Entry<String, String[]> e = (Map.Entry)var17.next();
//                    String key = (String)e.getKey();
//                    String[] values = (String[])e.getValue();
//                    if (PARAMETERS.equals(key)) {
//                        Map<String, Object> map = (Map) JSON.parseObject(((String[])parameterMap.get(key))[0], Map.class);
//                        Iterator var14 = map.entrySet().iterator();
//
//                        while(var14.hasNext()) {
//                            Map.Entry<String, Object> entry = (Map.Entry)var14.next();
//                            parameters.put((String)entry.getKey(), entry.getValue().toString());
//                        }
//                    }
//
//                    String stringValue;
//                    if (StringUtils.startsWith(key, PARAMETERS)) {
//                        stringValue = key.replaceAll("parameters\\[", "").replaceAll("]", "");
//                        parameters.put(stringValue, URLDecoder.decode(values[0], "UTF-8"));
//                    } else if (values.length <= 1) {
//                        stringValue = URLDecoder.decode(values[0], "UTF-8");
//                        objectNode.put(key, stringValue);
//                    } else {
//                        ArrayNode arrayNode = mapper.createArrayNode();
//
//                        for(int i = 0; i < values.length; ++i) {
//                            arrayNode.add(values[i]);
//                        }
//
//                        objectNode.set(key, arrayNode);
//                    }
//                }
//
//                objectNode.set(PARAMETERS, parameters);
//                restRequest = (Request)mapper.convertValue(objectNode, Request.class);
//                restResponse = (Response)this.handleRequest(restRequest, request);
//                break;
//            }
//        } catch (Exception var16) {
//            log.error("jsonp请求异常: objectNode={}", objectNode, var16);
//            if (restRequest == null) {
//                restRequest = new Request();
//                restRequest.setCmd(request.getParameter("cmd"));
//            }
//
//            restResponse = Response.builder().auth(Maps.newHashMap()).extraInfo(Maps.newHashMap()).cmd(restRequest.getCmd()).statusCode(500).build();
//            if (var16 instanceof ServiceException) {
//                ServiceException exception = (ServiceException)var16;
//                restResponse.setError(new RestError(exception.getErrorCode(), exception.getErrorInfo(), exception.getDesc()));
//            } else {
//                restResponse.setError(new RestError(RestErrorCode.SYSTEM_ERROR.getCode(), RestErrorCode.SYSTEM_ERROR.getMessage(), (Object)null));
//            }
//        }
//
//        RestHelpers.printJsonForJsonp(response, restRequest, restResponse);
//    }
//
//    @CrossOrigin(
//            origins = {"*"}
//    )
//    @RequestMapping(
//            method = {RequestMethod.POST},
//            produces = {"application/json; charset=UTF-8"}
//    )
//    public Object handleRequest(@RequestBody Request req, HttpServletRequest httpReq) {
//        try {
//            String cmd = req.getCmd();
//            this.checkCmdValid(cmd);
//            if (cmd.startsWith("test/")) {
//                cmd = this.configuration.getCgiServiceName() + "/" + cmd;
//            }
//
//            this.requestInit(req, httpReq);
//            Response response = Response.init(req);
//            RestServiceMapping serviceMapping = RestServiceUtil.getBeanMapping(cmd);
//            this.checkCmdExists(cmd, serviceMapping);
//            RestInvoker invoker = null;
//            RestRequest restRequest = null;
//
//            try {
//                if (serviceMapping.isLogRequest()) {
//                    this.logRequest(req);
//                }
//
//                invoker = RestServiceUtil.getInvoker(serviceMapping, req, response, httpReq);
//                restRequest = invoker.getRestRequest();
//                this.initThreadContext(req, response, serviceMapping, restRequest);
//                if (StringUtils.equals(req.getRunningMode(), "ping")) {
//                    response.setResponse("pong");
//                    return this.returnResponse(req.getResponseType(), response);
//                }
//
//                if (!cmd.startsWith(this.configuration.getCgiServiceName() + "/test/")) {
//                    Response prepareResult = invokePrepareInterceptors(invoker, this.configuration, response);
//                    if (prepareResult != null) {
//                        return this.returnResponse(req.getResponseType(), prepareResult);
//                    }
//                }

//                if (serviceMapping.getTimeout() > 0) {
//                    try {
//                        limiter.callWithTimeout(() -> {
//                            try {
//                                this.invokeBusiness(req, response, invoker);
//                            } catch (Exception var5) {
//                                this.handleException(invoker, req, response, var5);
//                            }
//
//                            return null;
//                        }, (long)serviceMapping.getTimeout(), TimeUnit.MILLISECONDS, true);
//                    } catch (UncheckedTimeoutException var10) {
//                        log.error("timeout", var10);
//                        RestError error = new RestError(RestErrorCode.RESPONSE_TIMEOUT.getCode(), RestErrorCode.RESPONSE_TIMEOUT.getMessage(), (Object)null);
//                        response.setError(error);
//                        return response;
//                    }
//                } else {
//                    this.invokeBusiness(req, response, invoker);
//                }
//            } catch (Exception var11) {
//                this.handleException(invoker, req, response, var11);
//            }
//
//            invokeAfterInterceptors(serviceMapping, restRequest, this.configuration, response);
//            return this.returnResponse(req.getResponseType(), response);
//        } catch (Exception e) {
//            throw new ServiceException(e.getMessage());
//        }
//    }

//    private void initThreadContext(Request req, Response response, RestServiceMapping serviceMapping, RestRequest bizRequest) {
//        ThreadContextHolder.setAttr("serviceMapping", serviceMapping);
//        ThreadContextHolder.setAttr("request", bizRequest);
//        ThreadContextHolder.setAttr("restRequest", req);
//        ThreadContextHolder.setAttr("response", response);
//    }

//    private void checkCmdExists(String cmd, RestServiceMapping serviceMapping) {
//        if (serviceMapping == null) {
//            throw new ServiceException(RestErrorCode.CMD_NOT_FOUND.getCode(), "cmd not found", cmd);
//        }
//    }
//
//    private void checkCmdValid(String cmd) {
//        if (StringUtils.isBlank(cmd)) {
//            throw new ServiceException(RestErrorCode.CMD_NULL.getCode(), RestErrorCode.CMD_NULL.getMessage());
//        }
//    }
//
//    private void handleException(RestInvoker invoker, Request request, Response response, Exception e) {
//        response.setException(e);
//        Integer retryTimes = (Integer)ThreadContextHolder.getAttr("retryTimes");
//        if (retryTimes != null && retryTimes > 0) {
//            log.info("cmd: {}, retry.times: {}", invoker.getCmd(), retryTimes);
//
//            try {
//                ThreadContextHolder.setAttr("retryTimes", retryTimes - 1);
//                this.invokeBusiness(request, response, invoker);
//            } catch (Exception var7) {
//                this.handleException(invoker, request, response, var7);
//            }
//        }
//
//        RestHelpers.handleException(request, response, e);
//    }
//
//    private void requestInit(Request request, HttpServletRequest httpRequest) {
//        request.setRequestId(ShortUUID.randomUUID());
//        request.setRequestTime(new Date());
//        request.setIp(HostUtil.getIpAddress(httpRequest));
//        if (!request.isDebug() || StringUtils.equals("product", PropertyFileUtil.get("system.env"))) {
//            request.setDebug(false);
//        }
//
//    }
//
//    private void logRequest(Request request) {
//        String originMima = "";
//        if (null != request.getParameters() && StringUtils.isNotBlank((String)request.getParameters().get(PASSWORD))) {
//            originMima = (String)request.getParameters().get(PASSWORD);
//            request.getParameters().put(PASSWORD, "***");
//        }
//
//        String clientIp = request.getIp();
//        if (request.isDebug()) {
//            log.info("RECEIVE_REQUEST, ip:{}, content:\n{}", clientIp, JsonUtil.toJSONString(request, true));
//        } else {
//            log.info("RECEIVE_REQUEST, ip:{}, content: {}", clientIp, JsonUtil.toJSONString(request));
//        }
//
//        if (StringUtils.isNotBlank(originMima)) {
//            request.getParameters().put(PASSWORD, originMima);
//        }
//
//    }

//    private Object returnResponse(String responseType, Response res) {
//        ThreadContextHolder.cleanAttrs();
//        if (StringUtils.equals(responseType, "origin")) {
//            return res.getResponse();
//        } else if (StringUtils.equals(responseType, "responseOnly")) {
//            Map resultMap = Maps.newHashMap();
//            resultMap.put("response", res.getResponse());
//            return resultMap;
//        } else {
//            return res;
//        }
//    }
//
//    private Object invokeBusiness(Request request, Response response, RestInvoker invoker) {
//        try {
//            RestServiceMapping serviceMapping = invoker.getServiceMapping();
//            Object[] methodParameters = invoker.getMethodParameters();
//            Object bean = serviceMapping.getBean();
//            Method method = serviceMapping.getMethod();
//            Object responseBody = method.invoke(bean, methodParameters);
//            if (responseBody instanceof Result) {
//                Result result = (Result)responseBody;
//                if (!result.isSuccess()) {
//                    if (serviceMapping.getResultType().equals(ResultType.SELF)) {
//                        responseBody = result;
//                    } else {
//                        RestError restError = new RestError(result.getErrorCode(), result.getErrorMessage(), result.getDesc());
//                        responseBody = null;
//                        response.setError(restError);
//                    }
//                } else if (serviceMapping.getResultType().equals(ResultType.DEFAULT)) {
//                    responseBody = result.getModel();
//                }
//            }
//
//            response.setResponse(responseBody);
//            Date responseTime = new Date();
//            response.setResponseTime(responseTime);
//            response.setDuration(responseTime.getTime() - request.getRequestTime().getTime());
//            return responseBody;
//        } catch (Exception e) {
//            throw new ServiceException(e.getMessage());
//        }
//    }
//
//    private static Response invokePrepareInterceptors(RestInvoker invoker, RestConfiguration configuration, Response response) {
//        String cmd = invoker.getCmd();
//        RestServiceMapping serviceMapping = invoker.getServiceMapping();
//        RestRequest restRequest = invoker.getRestRequest();
//        List<PrepareInterceptor> prepareInterceptors = configuration.getPrepareInterceptors();
//        Iterator var7 = prepareInterceptors.iterator();
//
//        boolean returnResult;
//        do {
//            if (!var7.hasNext()) {
//                return null;
//            }
//
//            PrepareInterceptor requestInterceptor = (PrepareInterceptor)var7.next();
//            String requestInterceptorName = requestInterceptor.getName();
//            long startTime = System.currentTimeMillis();
//            log.trace("REST_PREPARE_INTERCEPTOR_START, cmd: {}, name: [ {} ]", cmd, requestInterceptorName + "|" + requestInterceptor.getClass().getName());
//            returnResult = requestInterceptor.execute(serviceMapping, restRequest, response);
//            log.trace("REST_PREPARE_INTERCEPTOR_COMPLETE， cmd: {}, name: [ {} ], returnResult: {}, duration: {}",
//                    new Object[]{cmd, requestInterceptorName + "|" + requestInterceptor.getClass().getName(), returnResult, System.currentTimeMillis() - startTime});
//        } while(!returnResult);
//
//        return RestHelpers.returnResponse(restRequest, response, serviceMapping, configuration);
//    }
//
//    private static void invokeAfterInterceptors(RestServiceMapping serviceMapping, RestRequest restRequest, RestConfiguration configuration, Response response) {
//        List<AfterInterceptor> afterInterceptors = configuration.getAfterInterceptors();
//        Iterator var5 = afterInterceptors.iterator();
//
//        while(var5.hasNext()) {
//            AfterInterceptor requestInterceptor = (AfterInterceptor)var5.next();
//            String requestInterceptorName = requestInterceptor.getName();
//            log.trace("REST_AFTER_INTERCEPTOR_START, cmd: {}, name: {}", response.getCmd(), requestInterceptorName + "|" + requestInterceptor.getClass().getName());
//            long startTime = System.currentTimeMillis();
//            requestInterceptor.execute(serviceMapping, restRequest, response);
//            log.trace("REST_AFTER_INTERCEPTOR_COMPLETE， cmd: {}, name: {},duration: {}",
//                    new Object[]{response.getCmd(), requestInterceptorName + "|" + requestInterceptor.getClass().getName(), System.currentTimeMillis() - startTime});
//        }
//
//    }
}
