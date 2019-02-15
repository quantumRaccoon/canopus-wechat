package com.canopus.website.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Function implements Serializable {
    private Long id;

    private Long parentId;

    private String name;

    private Integer type;

    private Integer level;

    private Integer order;

    private String webUrl;

    private String icon;

    private Date createTime;

    private Long createUserId;

    private String createUserAccount;

    private Date updateTime;

    private Long updateUserId;

    private String updateUserAccount;

    private Integer validity;

    private static final long serialVersionUID = 1L;
}