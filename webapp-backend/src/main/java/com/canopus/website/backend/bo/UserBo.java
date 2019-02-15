package com.canopus.website.backend.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 14:34
 * @Description:
 */
@Data
public class UserBo implements Serializable {
    private Long id;

    private String account;

    private String icon;

    private String name;

    private String phone;

    private String email;

    private Integer status;
}
