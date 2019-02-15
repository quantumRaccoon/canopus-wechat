package com.canopus.website.api.utils;

import com.alibaba.fastjson.JSON;
import com.canopus.website.api.constants.RestErrorCode;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.model.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:01
 * @Description:
 */
@Slf4j
public final class RestServiceUtil {
    public static final Map<String, RestServiceMapping> SERVICE_MAPPING = Maps.newConcurrentMap();

    public static RestServiceMapping getBeanMapping(String cmd) {
        return (RestServiceMapping)SERVICE_MAPPING.get(cmd);
    }

    public static RestInvoker getInvoker(RestServiceMapping serviceMapping, Request request, Response response, HttpServletRequest httpRequest) {
        String cmd = request.getCmd();
        Class requestType = serviceMapping.getRequestType();
        RestRequest restRequest = getBaseRestRequest(requestType, httpRequest, request);
        Class[] methodParameterClasses;
        Object[] methodParameters;
        if (requestType == DynamicRestRequest.class) {
            Map<Integer, RestAnnotationParameter> annotationParameters = serviceMapping.getAnnotationParameters();
            Class<?>[] methodParameterTypes = serviceMapping.getParameterTypes();
            methodParameterClasses = new Class[methodParameterTypes.length];
            methodParameters = new Object[methodParameterTypes.length];

            for(int i = 0; i < methodParameterTypes.length; ++i) {
                Class<?> methodParameterType = methodParameterTypes[i];
                RestAnnotationParameter restAnnotationParameter = (RestAnnotationParameter)annotationParameters.get(i + 1);
                if (restAnnotationParameter != null) {
                    String annotationParamName = restAnnotationParameter.getName();
                    Object paramValue = request.getParameters().get(annotationParamName);
                    validParametersForParamAnnotation(response, restAnnotationParameter, annotationParamName, paramValue);
                    if (response.getError() == null) {
                        if (paramValue == null) {
                            methodParameters[i] = null;
                        } else {
                            methodParameters[i] = ConvertUtils.convert(paramValue, methodParameterType);
                        }

                        methodParameterClasses[i] = methodParameterType;
                    }
                } else if (RestRequest.class.isAssignableFrom(methodParameterType)) {
                    methodParameters[i] = restRequest;
                    methodParameterClasses[i] = methodParameterType;
                }
            }
        } else if (RestRequest.class.isAssignableFrom(requestType)) {
            methodParameterClasses = new Class[]{requestType};
            validParametersForValidatorAnnotation(response, restRequest);
            methodParameters = new Object[]{restRequest};
        } else {
            methodParameterClasses = new Class[]{requestType};
            validParametersForValidatorAnnotation(response, restRequest);
            methodParameters = new Object[]{restRequest.getRequest().getOriginParam()};
        }

        return new RestInvoker(cmd, serviceMapping, restRequest, methodParameterClasses, methodParameters);
    }

    private static RestRequest getBaseRestRequest(Class<? extends RestRequest> requestType, HttpServletRequest httpRequest, Request request) {
        try {
            RestRequest restRequest;
            Map parameters;
            String jsonString;
            if (RestRequest.class.isAssignableFrom(requestType)) {
                parameters = request.getParameters();
                jsonString = JSON.toJSONString(parameters);
                restRequest = (RestRequest)JSON.parseObject(jsonString, requestType);
                if (restRequest == null) {
                    restRequest = (RestRequest)requestType.newInstance();
                }

                restRequest.setHttpRequest(httpRequest);
                restRequest.setRequest(request);
            } else {
                restRequest = new RestRequest();
                parameters = request.getParameters();
                jsonString = JSON.toJSONString(parameters);
                request.setOriginParam(JSON.parseObject(jsonString, requestType));
                restRequest.setRequest(request);
            }

            return restRequest;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private static void validParametersForParamAnnotation(Response response, RestAnnotationParameter restAnnotationParameter, String annotationParamName, Object paramValue) {
        if (restAnnotationParameter.isRequired() && (paramValue == null || StringUtils.isBlank(paramValue.toString()))) {
            response.setStatusCode(400);
            response.setError(new RestError("2", "参数错误", (Object)null));
            response.setDebugInfo("param [" + annotationParamName + "] can't be null");
            response.setResponseTime(new Date());
        }

    }

    private static void validParametersForValidatorAnnotation(Response response, RestRequest restRequest) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RestRequest>> validate = validator.validate(restRequest, new Class[0]);
        if (!validate.isEmpty()) {
            response.setStatusCode(400);
            Map<String, Object> validateInfos = Maps.newHashMap();
            Iterator var6 = validate.iterator();

            while(var6.hasNext()) {
                ConstraintViolation<RestRequest> baseRestRequestConstraintViolation = (ConstraintViolation)var6.next();
                validateInfos.put(baseRestRequestConstraintViolation.getPropertyPath().toString(), baseRestRequestConstraintViolation.getMessage());
            }

            response.setError(new RestError(RestErrorCode.PARAM_ERROR.getCode(), RestErrorCode.PARAM_ERROR.getMessage(), (Object)null));
            response.setDebugInfo(validateInfos);
            response.setResponseTime(new Date());
        }

    }

    private static Integer getServerPort() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        Set objs;
        try {
            objs = mbs.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        } catch (MalformedObjectNameException var4) {
            log.error("get server port fail", var4);
            return null;
        }

        ObjectName next = (ObjectName)objs.iterator().next();
        if (next != null) {
            String port = next.getKeyProperty("port");
            return new Integer(port);
        } else {
            return null;
        }
    }

    public static Class<?> getTargetClass(Class<?> targetClass) {
        Assert.notNull(targetClass, "targetClass 不能为空 ");
        if (targetClass.getName().contains("$$")) {
            Class<?> superClass = targetClass.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }

        return targetClass;
    }

    private RestServiceUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
