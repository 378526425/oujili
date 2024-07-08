package com.wxmblog.api.auth.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-17 14:07
 **/

@Data
public class LoginUserResponse {

    private Boolean success;

    private String username;
}
