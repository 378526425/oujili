package com.wxmblog.nostalgia.common.rest.response.front.matching;

import com.wxmblog.nostalgia.common.enums.user.MatchingStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-04 14:35
 **/

@Data
public class LikeMePageResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "头像")
    private String headPortrait;

    @ApiModelProperty(value = "匹配状态 SUCCESS(\"匹配成功\"),\n" +
            "    LIKE_ME(\"喜欢我的\")")
    private MatchingStatusEnum matchingStatus;

    @ApiModelProperty(value = "描述信息")
    private String descInfo;

    private Boolean isUnlock;
}
