package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
@Data
@TableName(value = "university", autoResultMap = true)
public class UniversityEntity extends BaseEntity {

    /**
     * 学校名称
     */
    @TableField("name")
    private String name;

}
