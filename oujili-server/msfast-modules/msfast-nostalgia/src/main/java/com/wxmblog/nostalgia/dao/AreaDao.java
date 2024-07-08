package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.common.rest.response.front.area.AreaResponse;
import com.wxmblog.nostalgia.entity.AreaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 地区
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-26 13:40:17
 */
@Mapper
public interface AreaDao extends BaseMapper<AreaEntity> {

    List<AreaResponse> province(Map<String, Object> param);
}
