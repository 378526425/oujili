package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.nostalgia.entity.FrUserEntity;

public interface TestService extends IService<FrUserEntity> {

    void deleteFruser(Integer id);
}
