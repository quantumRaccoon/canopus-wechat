package com.canopus.website.service.impl;

import com.canopus.website.Constant;
import com.canopus.website.api.exception.ServiceException;
import com.canopus.website.dao.UserMapper;
import com.canopus.website.enums.ErrorCode;
import com.canopus.website.model.User;
import com.canopus.website.model.UserExample;
import com.canopus.website.service.UserService;
import com.canopus.website.utils.CommonUtils;
import com.canopus.website.utils.PageResult;
import com.canopus.website.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author: dai-ych
 * @Date: 2019/1/23 17:41
 * @Description:
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    public Long create(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        criteria.andValidityEqualTo(Constant.VALIDITY_YES);

        List<User> list = userMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException(ErrorCode.DATA_EXISTENCE_ERROR.getCode(), "用户名已存在，请重新输入");
        }
        String salt = CommonUtils.generateSalt();
        user.setStatus(Constant.USER_STATUS_DEFAULT);
        user.setSalt(salt);
        user.setPassword(CommonUtils.saltEncrypt(salt, user.getPassword()));
        user.setValidity(Constant.VALIDITY_YES);
        userMapper.insertSelective(user);
        return user.getId();
    }


    public PageResult page(User user, int page, int row){
        PageHelper.startPage(page, row);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andValidityEqualTo(Constant.VALIDITY_YES);
        if (null != user.getStatus()){
            criteria.andStatusEqualTo(user.getStatus());
        }
        if (StringUtils.isNotBlank(user.getAccount())){
            criteria.andAccountLike(user.getAccount());
        }
        if (StringUtils.isNotBlank(user.getName())){
            criteria.andNameLike(user.getName());
        }
        if (StringUtils.isNotBlank(user.getPhone())){
            criteria.andPhoneLike(user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getEmail())){
            criteria.andEmailLike(user.getEmail());
        }
        List<User> list = userMapper.selectByExample(example);
        PageInfo info = new PageInfo<>(list);
        return PageUtils.assemblyPageResult(info);
    }


    public List<User> selectByAccount(String account){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andValidityEqualTo(Constant.VALIDITY_YES);
        criteria.andAccountEqualTo(account);
        return userMapper.selectByExample(example);
    }
}
