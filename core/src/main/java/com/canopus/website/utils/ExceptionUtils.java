package com.canopus.website.utils;

import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.api.model.RestError;
import com.canopus.website.enums.ErrorCode;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 16:22
 * @Description:
 */
public class ExceptionUtils {

    public static ServiceException assemble(ErrorCode errorCode) {
        return new ServiceException(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 统一的错误码, 自定义的错误信息
     *
     * @param errorCode
     * @param message
     * @return
     */
    public static ServiceException assemble(ErrorCode errorCode, String message) {
        return new ServiceException(errorCode.getCode(), message);
    }

    public static RestError assembleRestError(ErrorCode errorCode) {
        return new RestError(errorCode.getCode(), errorCode.getMessage(), errorCode);
    }
}
