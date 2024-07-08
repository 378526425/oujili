package com.wxmblog.nostalgia.common.rest.request.article;

import com.wxmblog.base.file.annotation.FileListSave;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ArticleAddRequest {

    @Length(max = 500, message = "内容不能超过500字")
    private String content;

    @Size(max = 9, message = "最多9张图片")
    @FileListSave
    private List<String> img;

}
