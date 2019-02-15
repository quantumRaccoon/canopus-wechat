package com.canopus.website.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.canopus.website.api.utils.HostUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 13:41
 * @Description:
 */
@Slf4j
@Data
public final class Response {
    String requestId;
    String cmd;
    RestError error;
    int statusCode = 200;
    Object response;
    Date responseTime;
    long responseTimestamp;
    long duration;
    Object debugInfo;
    String clientIp;
    boolean debug;
    Map<String, Object> extraInfo = Maps.newHashMap();
    Map<String, Object> auth = Maps.newHashMap();
    Throwable exception;

    public static Response init(Request request) {
        Response response = builder().requestId(request.getRequestId()).cmd(request.getCmd()).statusCode(200).clientIp(request.getIp()).auth(Maps.newHashMap()).extraInfo(Maps.newHashMap()).build();
        if (request.isDebug()) {
            response.setDebug(true);
            response.getExtraInfo().put("serverIp", HostUtil.getServerIp());
        }

        response.getExtraInfo().put("serverName", HostUtil.getHostName());
        return response;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
        if (responseTime != null) {
            this.setResponseTimestamp(responseTime.getTime());
        }

    }

    @JSONField(
            serialize = false
    )
    @JsonIgnore
    public Throwable getException() {
        return this.exception;
    }

    @ConstructorProperties({"requestId", "cmd", "error", "statusCode", "response", "responseTime", "responseTimestamp", "duration", "debugInfo", "clientIp", "debug", "extraInfo", "auth", "exception"})
    Response(String requestId, String cmd, RestError error, int statusCode, Object response, Date responseTime, long responseTimestamp, long duration, Object debugInfo, String clientIp, boolean debug, Map<String, Object> extraInfo, Map<String, Object> auth, Throwable exception) {
        this.requestId = requestId;
        this.cmd = cmd;
        this.error = error;
        this.statusCode = statusCode;
        this.response = response;
        this.responseTime = responseTime;
        this.responseTimestamp = responseTimestamp;
        this.duration = duration;
        this.debugInfo = debugInfo;
        this.clientIp = clientIp;
        this.debug = debug;
        this.extraInfo = extraInfo;
        this.auth = auth;
        this.exception = exception;
    }

    public static Response.ResponseBuilder builder() {
        return new Response.ResponseBuilder();
    }

    public static class ResponseBuilder {
        private String requestId;
        private String cmd;
        private RestError error;
        private int statusCode;
        private Object response;
        private Date responseTime;
        private long responseTimestamp;
        private long duration;
        private Object debugInfo;
        private String clientIp;
        private boolean debug;
        private Map<String, Object> extraInfo;
        private Map<String, Object> auth;
        private Throwable exception;

        ResponseBuilder() {
        }

        public Response.ResponseBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Response.ResponseBuilder cmd(String cmd) {
            this.cmd = cmd;
            return this;
        }

        public Response.ResponseBuilder error(RestError error) {
            this.error = error;
            return this;
        }

        public Response.ResponseBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Response.ResponseBuilder response(Object response) {
            this.response = response;
            return this;
        }

        public Response.ResponseBuilder responseTime(Date responseTime) {
            this.responseTime = responseTime;
            return this;
        }

        public Response.ResponseBuilder responseTimestamp(long responseTimestamp) {
            this.responseTimestamp = responseTimestamp;
            return this;
        }

        public Response.ResponseBuilder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Response.ResponseBuilder debugInfo(Object debugInfo) {
            this.debugInfo = debugInfo;
            return this;
        }

        public Response.ResponseBuilder clientIp(String clientIp) {
            this.clientIp = clientIp;
            return this;
        }

        public Response.ResponseBuilder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Response.ResponseBuilder extraInfo(Map<String, Object> extraInfo) {
            this.extraInfo = extraInfo;
            return this;
        }

        public Response.ResponseBuilder auth(Map<String, Object> auth) {
            this.auth = auth;
            return this;
        }

        public Response.ResponseBuilder exception(Throwable exception) {
            this.exception = exception;
            return this;
        }

        public Response build() {
            return new Response(this.requestId, this.cmd, this.error, this.statusCode, this.response, this.responseTime,
                    this.responseTimestamp, this.duration, this.debugInfo, this.clientIp, this.debug, this.extraInfo, this.auth, this.exception);
        }

        public String toString() {
            return "Response.ResponseBuilder(requestId=" + this.requestId + ", cmd=" + this.cmd + ", error=" + this.error +
                    ", statusCode=" + this.statusCode + ", response=" + this.response + ", responseTime=" +
                    this.responseTime + ", responseTimestamp=" + this.responseTimestamp + ", duration=" + this.duration +
                    ", debugInfo=" + this.debugInfo + ", clientIp=" + this.clientIp + ", debug=" + this.debug +
                    ", extraInfo=" + this.extraInfo + ", auth=" + this.auth + ", exception=" + this.exception + ")";
        }
    }
}
