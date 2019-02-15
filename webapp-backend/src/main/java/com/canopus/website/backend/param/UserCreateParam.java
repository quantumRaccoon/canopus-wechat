package com.canopus.website.backend.param;

import com.canopus.website.api.model.RestRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: dai-ych
 * @Date: 2019/1/25 13:24
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserCreateParam extends RestRequest implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Max(value = 20,message = "密码长度最长不能超过20位")
    @Min(value = 6,message = "密码长度最短不能少于6位")
    private String password;

    private String icon;

    private String name;

    private String phone;

    private String email;

    private Long createUserId;

    private String createUserAccount;

}
