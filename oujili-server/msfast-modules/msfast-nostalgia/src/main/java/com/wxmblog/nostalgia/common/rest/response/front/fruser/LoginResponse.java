package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmblog.base.common.enums.GenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-29 16:46
 **/

@Data
public class LoginResponse {

    private Integer id;

    /**
     * 头像
     */
    private String headPortrait;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 城市
     */
    private String city;


    private GenderEnum gender;

}
