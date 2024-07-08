package com.wxmblog.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.nostalgia.common.constant.Constants;
import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.AuthTypeEnum;
import com.wxmblog.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import com.wxmblog.nostalgia.common.rest.response.front.auth.AuthResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.AdditionalResponse;
import com.wxmblog.nostalgia.dao.FrUserAuthDao;
import com.wxmblog.nostalgia.entity.FrUserAuthEntity;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.wxmblog.nostalgia.service.FrUserAuthService;
import com.wxmblog.nostalgia.service.FrUserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("frUserAuthService")
public class FrUserAuthServiceImpl extends ServiceImpl<FrUserAuthDao, FrUserAuthEntity> implements FrUserAuthService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    FrUserService frUserService;

    @Autowired
    MsfFileService msfFileService;

    @Override
    @Transactional
    public void addAuth(DoubleAuthRequest request) {

        RLock lock = redissonClient.getLock(Constants.ADD_AUTH + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = frUserService.getById(TokenUtils.getOwnerId());
            if (frUserEntity != null) {
                FrUserAuthEntity frUserAuthEntity = new FrUserAuthEntity();
                BeanUtils.copyProperties(request, frUserAuthEntity);
                frUserAuthEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
                frUserAuthEntity.setUserId(frUserEntity.getId());

                FrUserAuthEntity oldAuth = getAuthByType(request.getAuthType());
                if (oldAuth != null) {
                    msfFileService.deleteImg(oldAuth.getImgList(), request.getImgList());
                }
                this.save(frUserAuthEntity);
                if (AuthTypeEnum.EducationAuth.equals(request.getAuthType())) {
                    if (frUserEntity.getAdditional() != null) {
                        frUserEntity.getAdditional().setEducationAuth(AuthStatusEnum.EXAMINE);
                    } else {
                        AdditionalResponse additional = new AdditionalResponse();
                        additional.setEducationAuth(AuthStatusEnum.EXAMINE);
                        frUserEntity.setAdditional(additional);
                    }
                } else if (AuthTypeEnum.IdentityAuth.equals(request.getAuthType())) {
                    if (frUserEntity.getAdditional() != null) {
                        frUserEntity.getAdditional().setIdentityAuth(AuthStatusEnum.EXAMINE);
                    } else {
                        AdditionalResponse additional = new AdditionalResponse();
                        additional.setIdentityAuth(AuthStatusEnum.EXAMINE);
                        frUserEntity.setAdditional(additional);
                    }
                }
                frUserService.updateById(frUserEntity);
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public AuthResponse info() {
        AuthResponse authResponse = new AuthResponse();

        FrUserAuthEntity identityAuth = getAuthByType(AuthTypeEnum.IdentityAuth);
        if (identityAuth != null) {
            authResponse.setIdentityImgList(identityAuth.getImgList());
        }

        FrUserAuthEntity educationAuth = getAuthByType(AuthTypeEnum.EducationAuth);
        if (educationAuth != null) {
            authResponse.setEducationImgList(educationAuth.getImgList());
        }
        return authResponse;
    }

    @Override
    public FrUserAuthEntity getUserAuth(Integer userId, AuthTypeEnum authType, AuthStatusEnum authStatus) {
        Wrapper<FrUserAuthEntity> wrapper = new QueryWrapper<FrUserAuthEntity>().lambda()
                .eq(FrUserAuthEntity::getAuthType, authType)
                .eq(FrUserAuthEntity::getUserId, userId)
                .eq(FrUserAuthEntity::getAuthStatus, authStatus)
                .orderByDesc(FrUserAuthEntity::getId)
                .last(" limit 1");
        FrUserAuthEntity frUserAuthEntity = this.baseMapper.selectOne(wrapper);
        return frUserAuthEntity;
    }

    private FrUserAuthEntity getAuthByType(AuthTypeEnum typeEnum) {

        Wrapper<FrUserAuthEntity> queryWrapper = new QueryWrapper<FrUserAuthEntity>().lambda()
                .eq(FrUserAuthEntity::getUserId, TokenUtils.getOwnerId())
                .eq(FrUserAuthEntity::getAuthType, typeEnum)
                .orderByDesc(FrUserAuthEntity::getId)
                .last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

}
