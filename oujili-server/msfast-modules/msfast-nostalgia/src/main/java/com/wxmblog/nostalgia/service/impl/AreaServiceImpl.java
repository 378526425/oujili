package com.wxmblog.nostalgia.service.impl;

import com.wxmblog.nostalgia.common.rest.response.front.area.AreaResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.AreaDao;
import com.wxmblog.nostalgia.entity.AreaEntity;
import com.wxmblog.nostalgia.service.AreaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, AreaEntity> implements AreaService {


    @Override
    public List<AreaResponse> province() {
        Map<String, Object> param = new HashMap<>();
        param.put("province", true);
        return this.baseMapper.province(param);
    }

    @Override
    public List<AreaResponse> sonArea(String parentCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("parentCode", parentCode);
        return this.baseMapper.province(param);
    }
}
