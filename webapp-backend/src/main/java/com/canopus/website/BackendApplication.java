package com.canopus.website;


import com.canopus.website.api.config.RestConfiguration;
import com.canopus.website.api.config.RestConfigurationImpl;
import com.canopus.website.interceptor.AfterInterceptor;
import com.canopus.website.interceptor.AuthorityInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = ("com.canopus.website.*"))
@MapperScan("com.canopus.website.dao")
@EnableTransactionManagement
@EnableScheduling
public class BackendApplication {

    @Resource
    private AuthorityInterceptor authorityInterceptor;
    @Resource
    private AfterInterceptor validateInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @Bean
    public RestConfiguration RestServiceConfiguration() {

        RestConfiguration restServiceConfiguration = new RestConfigurationImpl();
        restServiceConfiguration.setCgiServiceName("website-backend");
        restServiceConfiguration.getPrepareInterceptors().add(authorityInterceptor);
        restServiceConfiguration.getAfterInterceptors().add(validateInterceptor);

        return restServiceConfiguration;
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("10240KB");
        //设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

}

