package com.canopus.website.backend.rest.web;

import com.canopus.website.api.model.Result;
import com.canopus.website.backend.ao.UserAo;
import com.canopus.website.backend.param.UserCreateParam;
import com.canopus.website.backend.param.UserParam;
import com.canopus.website.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @author: dai-ych
 * @date: 2019/1/23 17:42
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserAo userAo;


    /**
     * 创建管理员账号
     */
    @PostMapping("create")
    @Transactional
    public Long create(@RequestBody UserCreateParam param) throws Exception {
        log.info("[POST调用][website-backend/user/create], param: {}", param);
        return userAo.create(param);
    }


    /**
     * 查询管理员账号列表
     */
    @PostMapping("page")
    public Result page(@RequestBody UserParam param) {
        log.info("[POST调用][website-backend/user/page], param: {}", param);
        Result<PageResult> result = new Result<>();
        result.setSuccess(true);
        result.setModel(userAo.page(param));
        return result;
    }


    /**
     * 账号启用/停用
     */
    @PutMapping("updateStatus")
    @Transactional
    public void updateStatus(@RequestBody UserParam param) {
        log.info("[POST调用][website-backend/user/page], param: {}", param);
        userAo.updateStatus(param.getId(), param.getStatus());
    }


}
