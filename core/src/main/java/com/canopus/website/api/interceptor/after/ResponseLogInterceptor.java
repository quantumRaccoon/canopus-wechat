package com.canopus.website.api.interceptor.after;

import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.model.Request;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.utils.ThreadContextHolder;
import com.canopus.website.api.utils.JsonUtil;
import com.canopus.website.api.utils.RestServiceMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:04
 * @Description:
 */
@Slf4j
public class ResponseLogInterceptor extends AbstractAfterInterceptor{
    public ResponseLogInterceptor() {
    }

    public String getName() {
        return "记录请求响应日志";
    }

    public boolean execute() {
        RestServiceMapping serviceMapping = (RestServiceMapping)ThreadContextHolder.getAttr("serviceMapping");
        Request req = (Request)ThreadContextHolder.getAttr("restRequest");
        Response res = (Response) ThreadContextHolder.getAttr("response");
        String requestId = res.getRequestId();
        if (serviceMapping.isLogResponse()) {
            String jsonString;
            if (req.isDebug()) {
                jsonString = JsonUtil.toJSONString(res, true);
            } else {
                jsonString = JsonUtil.toJSONString(res);
            }

            log.info("REST_RESP, cmd: {}, thread: {}, requestId: {}, response: {}", new Object[]{req.getCmd(), Thread.currentThread().getName(), requestId, jsonString});
        }

        return false;
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }
}
