package com.canopus.website.api.interceptor.after;

import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.utils.ThreadContextHolder;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 15:07
 * @Description:
 */
public class ResponseTimeInfoInterceptor extends AbstractAfterInterceptor{
    public ResponseTimeInfoInterceptor() {
    }

    public String getName() {
        return "设置请求响应时间";
    }

    public boolean execute() {
        Response response = (Response) ThreadContextHolder.getAttr("response");
        long duration = response.getDuration();
        if (duration < 1000L) {
            response.getExtraInfo().put("consuming", duration + "毫秒");
        } else {
            response.getExtraInfo().put("consuming", (double)duration / 1000.0D + "秒");
        }

        ThreadContextHolder.setAttr("response", response);
        return false;
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }
}
