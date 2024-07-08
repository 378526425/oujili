package com.wxmblog.nostalgia.aspect;

import com.wxmblog.base.common.service.ICommonAspect;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.nostalgia.service.FrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonAspect implements ICommonAspect {

    @Autowired
    FrUserService frUserService;

    @Override
    @Transactional
    public void afterReturning() {

        Integer userId = TokenUtils.getOwnerId();
        if (userId != null) {
            this.frUserService.updateLatelyTime(userId);
        }

    }
}
