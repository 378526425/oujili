package com.wxmblog.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxmblog.nostalgia.dao.RecommendConfigDao;
import com.wxmblog.nostalgia.entity.RecommendConfigEntity;
import com.wxmblog.nostalgia.service.RecommendConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("recommendConfigService")
public class RecommendConfigServiceImpl extends ServiceImpl<RecommendConfigDao, RecommendConfigEntity> implements RecommendConfigService {

    @Override
    public RecommendConfigEntity getRecommendConfigByUserId(Integer userId) {
        Wrapper<RecommendConfigEntity> wrapper = new QueryWrapper<RecommendConfigEntity>().lambda().eq(RecommendConfigEntity::getUserId, userId);
        return this.baseMapper.selectOne(wrapper);
    }

}
