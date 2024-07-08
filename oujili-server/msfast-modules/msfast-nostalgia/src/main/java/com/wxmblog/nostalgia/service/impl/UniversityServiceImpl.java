package com.wxmblog.nostalgia.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.nostalgia.common.enums.user.SysConfigCodeEnum;
import com.wxmblog.nostalgia.common.rest.response.front.profession.ProfessionResponse;
import com.wxmblog.nostalgia.common.rest.response.front.university.UniversityListResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.UniversityDao;
import com.wxmblog.nostalgia.entity.UniversityEntity;
import com.wxmblog.nostalgia.service.UniversityService;

import java.util.List;


@Service("universityService")
public class UniversityServiceImpl extends ServiceImpl<UniversityDao, UniversityEntity> implements UniversityService {

    @Autowired
    MsfConfigService msfConfigService;

    @Override
    public List<UniversityListResponse> nameSelect(String name) {

        return this.baseMapper.nameSelect(name + "%");
    }

    @Override
    public List<ProfessionResponse> professionSelect() {

        String value = msfConfigService.getValueByCode(SysConfigCodeEnum.professionSelect.name());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, ProfessionResponse.class);
        }
        return null;
    }
}
