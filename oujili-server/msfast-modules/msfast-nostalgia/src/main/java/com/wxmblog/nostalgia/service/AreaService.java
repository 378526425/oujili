package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.nostalgia.common.rest.response.front.area.AreaResponse;
import com.wxmblog.nostalgia.entity.AreaEntity;

import java.util.List;


/**
 * 地区
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-26 13:40:17
 */
public interface AreaService extends IService<AreaEntity> {

    List<AreaResponse> province();

    List<AreaResponse> sonArea(String parentCode);
}

