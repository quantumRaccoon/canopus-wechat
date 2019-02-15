package com.canopus.website.api.interceptor.after;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.canopus.website.api.config.RestConfiguration;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.model.Request;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.mq.rocket.MsgSender;
import com.canopus.website.api.utils.*;
import com.canopus.website.api.utils.HostUtil;
import com.canopus.website.api.utils.JsonUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:07
 * @Description:
 */
@Slf4j
public class AlarmInterceptor extends AbstractAfterInterceptor{
    private MsgSender msgSender = (MsgSender) SpringContextHolder.getBean(MsgSender.class);
    private RestConfiguration restConfiguration = (RestConfiguration)SpringContextHolder.getBean(RestConfiguration.class);

    public AlarmInterceptor() {
    }

    public boolean execute() {
        Response response = (Response) ThreadContextHolder.getAttr("response");
        Throwable cause = response.getException();
        Throwable rootCause = ExceptionUtils.getRootCause(cause);
        rootCause = rootCause == null ? cause : rootCause;
        if (rootCause != null && !(rootCause instanceof ServiceException)) {
            this.alarmExceptionUseEmail(rootCause);
        }

        this.alarmForLongtimeRequest();
        return false;
    }

    protected void alarmForLongtimeRequest() {
        Response response = (Response)ThreadContextHolder.getAttr("response");
        boolean emailNotify = BooleanUtils.toBoolean("false");
        if (emailNotify) {
            long duration = response.getDuration();
            int alarmDuration = 5000;
            if (alarmDuration > 0 && duration > (long)alarmDuration) {
                this.alarmLongTimeUseEmail((int)duration, alarmDuration);
            }

        }
    }

    private Map<String, String> getDefaultAlarmParams() {
        String cmd = (String)ThreadContextHolder.getAttr("cmd");
        Request request = (Request)ThreadContextHolder.getAttr("restRequest");
        Map<String, String> templateParams = new HashMap();
        templateParams.put("cmd", cmd);
        templateParams.put("serverIp", HostUtil.getServerIp());
        templateParams.put("hostName", HostUtil.getHostName());
        templateParams.put("systemEnv", PropertyFileUtil.get("system.env"));
        templateParams.put("serverTime", DateUtil.formatNow("yyyy-MM-dd HH:mm:ss"));
        templateParams.put("requestContent", JsonUtil.toJSONString(request, true));
        return templateParams;
    }

    private void alarmLongTimeUseEmail(int duration, int alarmDuration) {
        Response response = (Response)ThreadContextHolder.getAttr("response");
        Map<String, String> templateParams = this.getDefaultAlarmParams();
        templateParams.put("responseContent", JsonUtil.toJSONString(response, true));
        templateParams.put("duration", String.valueOf(duration));
        templateParams.put("alarmDuration", String.valueOf(alarmDuration));
    }

    protected void alarmExceptionUseEmail(Throwable cause) {
        String cmd = (String)ThreadContextHolder.getAttr("cmd");
        Response response = (Response)ThreadContextHolder.getAttr("response");
        Map<String, String> templateParams = this.getDefaultAlarmParams();
        templateParams.put("responseContent", JsonUtil.toJSONString(response, true));
        String stackTrace = ExceptionUtils.getStackTrace(cause);
        stackTrace = StringUtils.substringBefore(stackTrace, "RestServiceController");
        stackTrace = StringUtils.replace(stackTrace, System.getProperty("line.separator"), "<br/>\n");
        templateParams.put("stackTrace", stackTrace);
        String mailTo = "";
        String emailTitle = TemplateUtil.parse("API告警 -[${systemEnv}] [${cmd}] [${hostName}]", templateParams);
        String content = "<p><strong style=\"color:red\">cmd:</strong> ${cmd}</p>\n<p><strong style=\"color:red\">time: </strong>${serverTime}</p>\n<p><strong style=\"color:red\">请求报文: </strong>${requestContent}</p>\n<p><strong style=\"color:red\">响应报文: </strong>${responseContent}</p>\n<p><strong style=\"color:red\">stackTrace: </strong>${stackTrace}</p>";
        String emailContent = TemplateUtil.parse(content, templateParams);
        Map<String, Object> map = Maps.newHashMap();
        map.put("businessCode", "error");
        map.put("businessNo", cmd);
        map.put("mailTo", mailTo);
        map.put("title", emailTitle);
        map.put("content", emailContent);
        map.put("priority", 1);
        map.put("channel", "SYS");
        String jsonMsg = JSON.toJSONString(map, new SerializerFeature[]{SerializerFeature.WriteClassName});

//        this.msgSender.sendMessage(jsonMsg, "MAIL", "SEND");
    }

    public String getName() {
        return "告警拦截器";
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }
}
