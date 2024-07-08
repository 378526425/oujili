package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.nostalgia.entity.RecommendConfigEntity;


/**
 * 推荐配置
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
public interface RecommendConfigService extends IService<RecommendConfigEntity> {

    RecommendConfigEntity getRecommendConfigByUserId(Integer userId);


}

