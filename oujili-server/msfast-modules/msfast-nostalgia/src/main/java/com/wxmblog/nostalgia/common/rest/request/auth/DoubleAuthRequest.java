package com.wxmblog.nostalgia.common.rest.request.auth;

import com.wxmblog.base.file.annotation.FileListSave;
import com.wxmblog.nostalgia.common.enums.user.AuthTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-03 17:18
 **/

@Data
public class DoubleAuthRequest {

    @ApiModelProperty(value = "认证类别 IdentityAuth(\"实名认证\"),\n" +
            "    EducationAuth(\"学历认证\")")
    @NotNull
    private AuthTypeEnum authType;

    @ApiModelProperty(value = "材料")
    @NotEmpty
    @FileListSave
    @Size(max = 4)
    private List<String> imgList;
}
