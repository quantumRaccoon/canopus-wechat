package com.canopus.website.backend.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: dai-ych
 * @Date: 2019/1/24 17:08
 * @Description:
 */
@Controller
public class IndexController {


    /**
     * 启动页
     */
    @RequestMapping("/health.html")
    public String index() {
        return "/welcomeToCanopus";
    }

}
