package com.canopus.website.backend.ao;

import com.canopus.website.Constant;
import com.canopus.website.backend.bo.UserBo;
import com.canopus.website.backend.param.UserCreateParam;
import com.canopus.website.backend.param.UserParam;
import com.canopus.website.dao.UserMapper;
import com.canopus.website.model.User;
import com.canopus.website.service.UserService;
import com.canopus.website.utils.CglibBeanCopierUtils;
import com.canopus.website.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: dai-ych
 * @Date: 2019/1/25 13:39
 * @Description:
 */
@Slf4j
@Service
public class UserAo {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    public Long create(UserCreateParam param) {
        User user = CglibBeanCopierUtils.copyProperties(param, User.class);
        // TODO 暂时写死，后续从redis取
        user.setCreateUserId((long) 1);
        user.setCreateUserAccount(Constant.ADMIN);
        return userService.create(user);
    }

    /**
     * 管理员信息分页查询
     */
    public PageResult page(UserParam param) {
        User user = CglibBeanCopierUtils.copyProperties(param, User.class);
        PageResult pageResult = userService.page(user, param.getPage(), param.getRows());
        List<UserBo> list = CglibBeanCopierUtils.copyProperties(pageResult.getList(), UserBo.class);
        pageResult.setList(list);
        return pageResult;
    }

    public void updateStatus(Long id, Integer status) {
        User user = userMapper.selectByPrimaryKey(id);
        if (null == user) {

        }
    }
}


