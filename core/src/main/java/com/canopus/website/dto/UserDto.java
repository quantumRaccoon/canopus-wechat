package com.canopus.website.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/1/25 14:57
 * @Description:
 */
@Data
public class UserDto implements Serializable {
    private Long id;

    private Long account;

    private String password;

    private String name;

    private Integer type;

    private String phone;

    private String email;

    private Integer status;

    private String tokenWeb;

    private Integer a;
}
