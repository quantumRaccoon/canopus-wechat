package com.canopus.website.config;

import com.canopus.website.config.handler.RestReturnValueHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 17:36
 * @Description:
 */
//@Configuration
public class ResponseConfig  implements WebMvcConfigurer {

//    @Autowired
//    private RestReturnValueHandler restReturnValueHandler;
//
//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
//        handlers.clear();
//        handlers.add(restReturnValueHandler);
//    }
}
