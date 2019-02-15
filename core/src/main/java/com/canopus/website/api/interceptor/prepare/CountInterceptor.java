package com.canopus.website.api.interceptor.prepare;

import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.model.Request;
import com.canopus.website.api.utils.DateUtil;
import com.canopus.website.api.utils.RestCountUtil;
import com.canopus.website.api.utils.ThreadContextHolder;
import com.canopus.website.api.utils.HostUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:29
 * @Description:
 */
public class CountInterceptor extends AbstractPrepareInterceptor{
    public CountInterceptor() {
    }

    public boolean execute() {
        Request restRequest = (Request) ThreadContextHolder.getAttr("restRequest");
        String cmd = restRequest.getCmd();
        String hostname = HostUtil.getHostName();
        String today = DateUtil.formatNow("yyyyMMdd");
        if (this.processPing(cmd, restRequest, today)) {
            return false;
        } else {
            String redisCountKey = "apiCount:" + today + ":cmds";
            RestCountUtil.addCountKey("apiCount:" + today + ":cmds", cmd);
            RestCountUtil.incr(redisCountKey);
            redisCountKey = "apiCount:" + today + ":cmd:" + cmd;
            RestCountUtil.incr(redisCountKey);
            redisCountKey = "apiCount:" + today + ":host:" + hostname;
            RestCountUtil.incr(redisCountKey);
            redisCountKey = "apiCount:" + today + ":host:" + cmd + ":" + hostname;
            RestCountUtil.incr(redisCountKey);
            String clientType = restRequest.getClientType();
            if (StringUtils.isNotBlank(clientType)) {
                redisCountKey = "apiCount:" + today + ":cmd-" + clientType + ":" + cmd;
                RestCountUtil.incr(redisCountKey);
                redisCountKey = "apiCount:" + today + ":host-" + clientType + ":" + cmd + ":" + hostname;
                RestCountUtil.incr(redisCountKey);
            }

            return false;
        }
    }

    private boolean processPing(String cmd, Request restRequest, String today) {
        if (StringUtils.equals(restRequest.getRunningMode(), "ping")) {
            String redisCountKey = "apiCount:" + today + ":ping";
            RestCountUtil.incr(redisCountKey);
            redisCountKey = "apiCount:" + today + ":ping:cmds:" + cmd;
            RestCountUtil.incr(redisCountKey);
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return "CMD访问统计";
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }
}
