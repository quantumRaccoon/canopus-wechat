package com.canopus.website.backend.ao;


import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.backend.param.UserCreateParam;
import com.canopus.website.dao.UserMapper;
import com.canopus.website.enums.ErrorCode;
import com.canopus.website.model.User;
import com.canopus.website.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: dai-ych
 * @Date: 2019/1/31 9:52
 * @Description:
 */
@Slf4j
@Service
public class LoginAo {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    public List<Map<String,Object>> login(UserCreateParam param){
        List<User> userList = userService.selectByAccount(param.getAccount());
        if (CollectionUtils.isEmpty(userList)){
            throw new ServiceException(ErrorCode.NO_PERMISSION_ERROR.getCode(),"账号不存在，请重新输入");
        }
        return null;
    }
}
