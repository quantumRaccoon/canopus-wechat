package com.canopus.website.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Client implements Serializable {
    private Long id;

    private String account;

    private String password;

    private String nickName;

    private String name;

    private Integer level;

    private String phone;

    private String email;

    private Date birth;

    private Integer status;

    private String tokenWeb;

    private String salt;

    private Date createTime;

    private Date updateTime;

    private Integer validity;

    private static final long serialVersionUID = 1L;

}