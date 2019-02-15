package com.canopus.website.backend.rest.web;

import com.canopus.website.api.annoations.RestMethod;
import com.canopus.website.api.model.Result;
import com.canopus.website.backend.ao.LoginAo;
import com.canopus.website.api.annoations.RestClass;
import com.canopus.website.api.annoations.RestParam;
import com.canopus.website.backend.param.UserCreateParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/25 11:11
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("admin")
public class LoginController {

    @Autowired
    private LoginAo loginAo;


    @Transactional
    @PostMapping("login")
    public List<Map<String,Object>> login(@RequestBody UserCreateParam param) throws Exception{
        log.info("[POST调用][/index/login], account: {}", param.getAccount());
        return loginAo.login(param);
    }
}
