package com.canopus.website.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 13:40
 * @Description:
 */
@Data
public class Request implements Serializable {
    private String cmd;
    private transient Map<String, Object> parameters = Maps.newHashMap();
    @JsonIgnore
    private transient Object originParam;
    private String callback;
    private String requestId;
    private String userToken;
    private String deviceToken;
    private String resolution;
    private String locationXy;
    private transient Map<String, Object> deviceInfo = Maps.newHashMap();
    private String appVersion;
    private Date requestTime;
    private String ip;
    private boolean debug = true;
    private String clientIdentifierCode;
    private String clientType;
    private String runningMode = "normal";
    private String appDomain = "";
    private String server = "";
    private int page = 0;
    private int rows = 10;
    private String responseType;
}
