package com.wxmblog.nostalgia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.FrUserDao;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.wxmblog.nostalgia.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-17 10:17
 **/

@Service
public class TestServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements TestService {

    @Transactional
    @Override
    public void deleteFruser(Integer id) {
        this.baseMapper.deleteById(id);
    }
}
