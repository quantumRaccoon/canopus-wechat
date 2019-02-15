package com.canopus.website.interceptor;

import com.canopus.website.api.interceptor.after.AbstractAfterInterceptor;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestRequest;
import com.canopus.website.api.utils.RestServiceMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * author: zhaokl
 * <p>
 * created at: 08/08/2017 17:27
 * <p>
 * desc: 校验器生成的错误信息默认放在debugInfo中, 将特定的参数错误信息, 重新写入error, 方便前端;
 * 比较土, 更优雅的方法寻找中
 */
@Slf4j
@Component
public class AfterInterceptor extends AbstractAfterInterceptor {

    @Override
    public boolean execute(RestServiceMapping serviceMapping, RestRequest request, Response response) {

        // 如果有返回内容, 则消除错误体
        if (null != response.getResponse()) {
            response.setError(null);
        }
        response.setDebugInfo(null);
        return false;
    }

    @Override
    protected boolean execute() {

        return false;
    }

    @Override
    public String getName() {

        return null;
    }
}
