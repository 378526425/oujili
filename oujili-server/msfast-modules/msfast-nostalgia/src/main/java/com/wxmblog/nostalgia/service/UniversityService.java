package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.nostalgia.common.rest.response.front.profession.ProfessionResponse;
import com.wxmblog.nostalgia.common.rest.response.front.university.UniversityListResponse;
import com.wxmblog.nostalgia.entity.UniversityEntity;

import java.util.List;


/**
 * 大学
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-20 15:49:55
 */
public interface UniversityService extends IService<UniversityEntity> {

    List<UniversityListResponse> nameSelect(String name);

    List<ProfessionResponse> professionSelect();
}

