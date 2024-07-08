package com.wxmblog.nostalgia.authority;

import com.wxmblog.base.auth.authority.service.IAuthorityServiceImpl;
import com.wxmblog.base.auth.authority.service.WxAppletService;
import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.entity.LoginUser;
import com.wxmblog.base.common.enums.BaseUserExceptionEnum;
import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.service.BaseCommonService;
import com.wxmblog.base.common.utils.DateUtils;
import com.wxmblog.base.file.service.MsfFileService;
import com.wxmblog.base.websocket.common.constant.WebSocketConstants;
import com.wxmblog.nostalgia.common.enums.user.AuthStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.EducationalTypeEnum;
import com.wxmblog.nostalgia.common.enums.user.UserTypeEnum;
import com.wxmblog.nostalgia.common.rest.request.fruser.AppletRegisterRequest;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.AdditionalResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.LoginResponse;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.wxmblog.nostalgia.service.FrUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class AuthorityServiceImpl extends IAuthorityServiceImpl<LoginRequest, AppletRegisterRequest> {

    @Autowired
    MsfFileService fileService;

    @Autowired
    FrUserService frUserService;

    @Autowired
    WxAppletService wxAppletService;

    @Autowired
    BaseCommonService baseCommonService;

    /*
     * @Author wanglei
     * @Description  微信小程序登录
     * @Date 16:49 2022/12/5
     * @Param
     * @return
     **/
    @Override
    public LoginUser login(LoginRequest loginRequest) {

        LoginUser loginUser = new LoginUser();
        FrUserEntity frUserEntity = this.frUserService.getFrUserByOpenId(loginRequest.getOpenId());
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }

        if (FrUserStatusEnum.DISABLE.equals(frUserEntity.getStatus())) {
            throw new JrsfException(BaseUserExceptionEnum.USER_STATUS_ERROR_EXCEPTION);
        }

        loginUser.setId(frUserEntity.getId());
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(frUserEntity, loginResponse);
        loginUser.setInfo(loginResponse);

        Map<String, Object> map = new HashMap<>();
        map.put(WebSocketConstants.HEAD_PORTRAIT, frUserEntity.getHeadPortrait());
        map.put(WebSocketConstants.NICK_NAME, frUserEntity.getNickName());
        baseCommonService.updateUser(frUserEntity.getId(), map);

        return loginUser;
    }

    @Override
    @Transactional
    public void wxAppletRegister(AppletRegisterRequest request) {

        FrUserEntity frUserEntity = new FrUserEntity();
        BeanUtils.copyProperties(request, frUserEntity);
        Integer age = DateUtils.getAgeByBirth(request.getBirthday());
        if (age < 16) {
            throw new JrsfException(BaseUserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("未满16周岁无法注册");
        }
        if (age > 100) {
            throw new JrsfException(BaseUserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("超过100周岁无法注册");
        }
        if (this.frUserService.countByOpenId(request.getOpenId()) > 0l) {
            throw new JrsfException(BaseUserExceptionEnum.USER_EXIST_EXCEPTION);
        }

        frUserEntity.setStatus(FrUserStatusEnum.ENABLE);
        frUserEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
        frUserEntity.setUserType(UserTypeEnum.Normal);
        frUserEntity.setEducationalType(EducationalTypeEnum.FullTime);

        AdditionalResponse additionalResponse = new AdditionalResponse();
        additionalResponse.setWaitApprovedStatus(AuthStatusEnum.EXAMINE);
        frUserEntity.setAdditional(additionalResponse);

        List<String> waitApprovedImg = new ArrayList<>();
        waitApprovedImg.add(request.getHeadPortrait());
        frUserEntity.setWaitApprovedImg(waitApprovedImg);
        this.frUserService.save(frUserEntity);

        //保存头像
        fileService.changeTempFile(request.getHeadPortrait());
        //保存地址配置信息
        frUserService.saveRecommendConfig(frUserEntity);

    }
}
