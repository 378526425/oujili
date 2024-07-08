package com.wxmblog.nostalgia.common.rest.request.article;

import lombok.Data;

@Data
public class ReplyRequest {

    private Integer userId;

    private Integer commentId;

    private Boolean reload;
}
