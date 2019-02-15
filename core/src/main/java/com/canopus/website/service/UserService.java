package com.canopus.website.service;

import com.canopus.website.model.User;
import com.canopus.website.utils.PageResult;

import java.util.List;

/**
 * @Author: dai-ych
 * @Date: 2019/1/23 17:41
 * @Description:
 */
public interface UserService {

    /**
     * 用户新增
     */
    Long create(User user);

    /**
     * 用户分页
     */
    PageResult page(User user,int page,int row);

    /**
     * 根据账号查询用户
     */
    List<User> selectByAccount(String account);
}
