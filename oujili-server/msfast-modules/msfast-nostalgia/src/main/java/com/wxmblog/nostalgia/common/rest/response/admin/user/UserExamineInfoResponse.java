package com.wxmblog.nostalgia.common.rest.response.admin.user;

import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-14 16:16
 **/

@Data
public class UserExamineInfoResponse extends BaseUserInfoResponse {

    @ApiModelProperty(value = "待审核资料认证状态 EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum waitApprovedStatus;

    @ApiModelProperty(value = "审核理由")
    private String remarks;

    @ApiModelProperty(value = "待审核相册")
    private List<String> waitApprovedImg;

    /**
     * 相册
     */
    @ApiModelProperty(value = "相册")
    private List<String> imgList;

    /**
     * 用户资料状态
     */
    @ApiModelProperty(value = "用户资料状态")
    private AuthStatusEnum authStatus;

    @ApiModelProperty(value = "版本")
    private Integer version;
}
