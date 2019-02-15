package com.canopus.website.interceptor;

import com.canopus.website.Constant;
import com.canopus.website.api.interceptor.prepare.AbstractPrepareInterceptor;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestRequest;
import com.canopus.website.api.utils.RestServiceMapping;
import com.canopus.website.enums.ErrorCode;
import com.canopus.website.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * author: zhaokl
 * <p>
 * created at: 08/08/2017 10:14
 * <p>
 * desc:
 */
@Slf4j
@Component
public class AuthorityInterceptor extends AbstractPrepareInterceptor {

//    @Resource
//    private RedisUtils redisUtils;
//
//    @Resource
//    private ITokenUtils iTokenUtils;


    /**
     * 不检查 iToken 的 cmd
     * 放在此处的 cmd 必须加明确说明, 用途
     */
    private static final String[] notCheckITokenCmdList = {
            // 获取验证码
            "website-backend/tenant/getVerifyImg"
            // 登录
            , "website-backend/index/login"
    };

    // 检查 iToken 但不检查权限的 cmd
    private static final String[] checkITokenButNotCheckFunctionCmdList = {
            "test"
    };


    /**
     * 1. 检查权限
     * 2. 检查参数
     */
    @Override
    public boolean execute(RestServiceMapping serviceMapping, RestRequest request, Response response) {
        log.debug("serviceMapping={}, request={}, response={}", serviceMapping, request, response);
        // 获取请求 cmd
        String cmd = request.getRequest().getCmd();
        // 获取请求 iToken
        String iToken = (String) request.getRequest().getParameters().get("iToken");
        // 无需 iToken
        if (Arrays.asList(notCheckITokenCmdList).contains(cmd)) {
            return false;
        }
//        // 需要 iToken
//        if (StringUtils.isBlank(iToken) || StringUtils.isBlank(redisUtils.get(iToken))) {
//            response.setStatusCode(500);
//            response.setError(ExceptionUtils.assembleRestError(ErrorCode.PERMISSION_DENY_MISSING_I_TOKEN));
//            return true;
//        }
//        String cmdSecondPart = cmd.split("/")[1];
        // 不需要验证权限
//        for (String s : checkITokenButNotCheckFunctionCmdList) {
//            if (s.equals(cmdSecondPart)) {
//                redisUtils.expire(iToken, Constant.I_TOKEN_EXPIRE_WEB);
//                return false;
//            }
//        }

        // TODO: 算法需要优化
        // 验证权限, 比对当前cmd 是否 包含在 当前iToken对应的功能权限中
//        String[] thisIds = redisUtils.get(Constant.REDIS_KEY_HEAD + cmd).split(": ")[1].split(",");
//        String[] functionIds = iTokenUtils.getInfoFromRedisByIToken("functionIds", iToken).split(",");
//        for (String f : functionIds) {
//            for (String t : thisIds) {
//                if (f.equals(t)) {
//                    redisUtils.expire(iToken, Constant.I_TOKEN_EXPIRE_WEB);
//                    return false;
//                }
//            }
//        }

        response.setStatusCode(500);
        response.setError(ExceptionUtils.assembleRestError(ErrorCode.NO_PERMISSION_ERROR));

        return true;
    }


    @Override
    public String getName() {
        return "website-backend 系统请求验证及授权拦截";
    }


    @Override
    protected boolean execute() {
        return false;
    }

}
