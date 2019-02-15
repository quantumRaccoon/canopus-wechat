package com.canopus.website.backend.param;

import com.canopus.website.api.model.RestRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/2/14 13:38
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserParam implements Serializable {

    private Long id;

    private String account;

    private String name;

    private String phone;

    private String email;

    private Integer status;

    private int page = 0;
    private int rows = 10;

}
