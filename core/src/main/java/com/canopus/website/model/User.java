package com.canopus.website.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;

    private Long roleId;

    private String account;

    private String password;

    private String icon;

    private String name;

    private String phone;

    private String email;

    private Integer status;

    private String tokenWeb;

    private String salt;

    private Long createUserId;

    private String createUserAccount;

    private Date createTime;

    private Long updateUserId;

    private String updateUserAccount;

    private Date updateTime;

    private Integer validity;

    private static final long serialVersionUID = 1L;

}