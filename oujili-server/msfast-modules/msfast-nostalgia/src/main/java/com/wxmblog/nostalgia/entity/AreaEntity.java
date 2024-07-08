package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * 地区
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-26 13:40:17
 */
@Data
@TableName(value = "area", autoResultMap = true)
public class AreaEntity extends BaseEntity {


    /**
     * 区域代码
     */
    @TableField("region_code")
    private String regionCode;
    /**
     * 区域名称
     */
    @TableField("region_name")
    private String regionName;
    /**
     * 父级代码
     */
    @TableField("parent_code")
    private String parentCode;
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

}
