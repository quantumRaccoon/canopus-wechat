package com.canopus.website.api.interceptor.prepare;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.canopus.website.api.annoations.Authority;
import com.canopus.website.api.constants.RestErrorCode;
import com.canopus.website.api.interceptor.InterceptorPurpose;
import com.canopus.website.api.model.Request;
import com.canopus.website.api.model.Response;
import com.canopus.website.api.model.RestError;
import com.canopus.website.api.utils.PropertyFileUtil;
import com.canopus.website.api.utils.SpringContextHolder;
import com.canopus.website.api.utils.ThreadContextHolder;
import com.canopus.website.api.utils.RestServiceMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 14:40
 * @Description:
 */
@Slf4j
public class AuthInterceptor extends AbstractPrepareInterceptor{
    private static final List<Authority> SET_USER_AUTHS = new ArrayList(2);
    private static final Map<String, ReferenceConfig> REFERENCE_CONFIG_CACHE = new HashMap(2);

    public AuthInterceptor() {
    }

    private static ReferenceConfig<GenericService> getReferenceConfig(String interfaceClass) {
        ReferenceConfig reference = (ReferenceConfig)REFERENCE_CONFIG_CACHE.get(interfaceClass);
        if (reference == null) {
            ApplicationConfig applicationConfig = (ApplicationConfig) SpringContextHolder.getBean(ApplicationConfig.class);
            RegistryConfig registryConfig = (RegistryConfig)SpringContextHolder.getBean(RegistryConfig.class);
            reference = new ReferenceConfig();
            reference.setInterface(interfaceClass);
            reference.setCheck(false);
            reference.setTimeout(3000);
            reference.setRetries(0);
            reference.setApplication(applicationConfig);
            reference.setRegistry(registryConfig);
            reference.setGeneric(true);
            REFERENCE_CONFIG_CACHE.put(interfaceClass, reference);
        }

        return reference;
    }

    public String getName() {
        return "权限拦截器";
    }

    public boolean execute() {
        try {
            RestServiceMapping serviceMapping = (RestServiceMapping) ThreadContextHolder.getAttr("serviceMapping");
            String userToken = (String)ThreadContextHolder.getAttr("userToken");
            String deviceToken = (String)ThreadContextHolder.getAttr("deviceToken");
            Response response = (Response)ThreadContextHolder.getAttr("response");
            Request request = (Request)ThreadContextHolder.getAttr("restRequest");
            Authority authority = serviceMapping.getAuthority();
            if (this.checkIgnore(authority)) {
                return false;
            }

            boolean maybeNeedUser = StringUtils.isNotBlank(userToken);
            ReferenceConfig reference;
            GenericService genericService;
            if (SET_USER_AUTHS.contains(authority) || maybeNeedUser) {
                if (StringUtils.isBlank(userToken)) {
                    response.setStatusCode(403);
                    response.setError(new RestError(RestErrorCode.NEED_TOKEN.getCode(), RestErrorCode.NEED_TOKEN.getMessage(), (Object)null));
                    return true;
                }

                try {
                    reference = getReferenceConfig("com.isesol.service.MemberCenterService");
                    genericService = (GenericService)reference.get();
                    Map<String, Object> result = (Map)genericService.$invoke("tokenVerify", new String[]{"java.lang.String"}, new Object[]{userToken});
                    if (result == null || !"true".equals(result.get("verify"))) {
                        response.setStatusCode(403);
                        response.setError(new RestError(RestErrorCode.TOKEN_INVALID.getCode(), RestErrorCode.TOKEN_INVALID.getMessage(), (Object)null));
                        if (request.isDebug()) {
                            response.setDebugInfo("token invalid");
                        }

                        response.getAuth().put("tokenValid", false);
                        return true;
                    }

                    Map<String, String> tokenInfo = (Map)result.get("tokenInfo");
                    ThreadContextHolder.setAttr("memberCode", tokenInfo.get("memberCode"));
                    ThreadContextHolder.setAttr("memberName", tokenInfo.get("memberName"));
                    response.getAuth().put("tokenValid", true);
                } catch (Exception var13) {
                    log.error("TokenVerifyService error", var13);
                    response.setStatusCode(500);
                    response.setError(new RestError(RestErrorCode.SYSTEM_ERROR.getCode(), RestErrorCode.SYSTEM_ERROR.getMessage(), (Object)null));
                    return true;
                }
            }

            if (StringUtils.isNotBlank(deviceToken)) {
                try {
                    reference = getReferenceConfig("com.i5portal.service.interfaces.IVerifyAndSendOrder");
                    genericService = (GenericService)reference.get();
                    String machineId = (String)genericService.$invoke("getMachineIdByToken", new String[]{"java.lang.String"}, new Object[]{deviceToken});
                    log.info("getMachineIdByToken deviceToken={},machineId={}", deviceToken, machineId);
                    if (!StringUtils.isBlank(machineId)) {
                        ThreadContextHolder.setAttr("equSerialNo", machineId);
                        response.getAuth().put("deviceTokenValid", true);
                    }
                } catch (Exception var12) {
                    log.error("getMachineIdByToken error", var12);
                    response.setStatusCode(500);
                    response.setError(new RestError(RestErrorCode.SYSTEM_ERROR.getCode(), RestErrorCode.SYSTEM_ERROR.getMessage(), (Object)null));
                    return true;
                }
            }
        } catch (Exception var14) {
            log.error("AuthInterceptor error", var14);
        }

        return false;
    }

    private boolean checkIgnore(Authority authority) {
        String apiEnv = PropertyFileUtil.get("api.env");
        return StringUtils.isNotBlank(apiEnv) && "local".equalsIgnoreCase(apiEnv) ? true : authority.equals(Authority.NONE);
    }

    public InterceptorPurpose getInterceptorPurpose() {
        return InterceptorPurpose.INTERNAL;
    }

    static {
        SET_USER_AUTHS.add(Authority.ANY);
        SET_USER_AUTHS.add(Authority.USER);
    }
}
