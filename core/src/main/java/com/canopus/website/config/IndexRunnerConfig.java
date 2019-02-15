package com.canopus.website.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: dai-ych
 * @Date: 2019/1/24 17:17
 * @Description:
 */
@Slf4j
@Component
public class IndexRunnerConfig  implements CommandLineRunner {

    @Value("${index.loginUrl}")
    private String loginUrl;

    @Value("${index.browserPath}")
    private String browserPath;

    @Value("${index.open}")
    private boolean isOpen;

    @Override
    public void run(String... args) throws Exception {
        if(isOpen){
            String cmd = browserPath +" "+ loginUrl;
            Runtime run = Runtime.getRuntime();
            try{
                run.exec(cmd);
                log.info("启动浏览器打开项目成功");
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

}
