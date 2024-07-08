package com.wxmblog.api.auth.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-17 14:09
 **/
@Data
public class LoginUserRequest {

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 用户密码
     */
    @NotBlank
    private String password;
}
