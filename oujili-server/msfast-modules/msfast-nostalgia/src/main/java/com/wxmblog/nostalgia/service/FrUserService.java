package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.nostalgia.common.rest.request.admin.statistic.UserRegisterStatisticRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserAdminInfoAddRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserInfoRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxmblog.nostalgia.common.rest.request.fruser.*;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserExamineRequest;
import com.wxmblog.nostalgia.common.rest.request.payment.SeeLikeMeRequest;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.OutlineResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.ProportionResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.*;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.*;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.UserInfoResponse;
import com.wxmblog.nostalgia.common.rest.response.front.payment.PayMenuResponse;
import com.wxmblog.nostalgia.common.rest.response.front.payment.ViewLikeMeResponse;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
public interface FrUserService extends IService<FrUserEntity> {

    Long countByOpenId(String openId);

    FrUserEntity getFrUserByOpenId(String openId);

    RecommendUserInfoResponse getRecommendUserInfo(RecommendUserRequest request);

    void saveRecommendConfig(FrUserEntity frUserEntity);

    RecommendConfigResponse getRecommendConfig();

    void updateConfigInfo(RecommendConfigRequest request);

    @Async
    void updateLatelyTime(Integer userId);

    PersonalCenterResponse getPersonalCenter();

    PersonalInfoResponse personalInfo();

    void photoEdit(PhotoEditRequest request);

    BaseInfoResponse baseInfo();

    void baseInfoEdit(BaseInfoEditRequest request);

    void characterEdit(CharacterRequest request);

    DoubleAuthResponse doubleAuth();

    UserInfoResponse userInfo(Integer id);

    void examine(UserExamineRequest request);

    PageResult<UserPageResponse> examinePage(UserPageRequest request, Integer pageIndex, Integer pageSize);

    UserExamineInfoResponse getExamineInfo(Integer id);

    PageResult<UserIdentityPageResponse> identityPage(UserPageRequest request, Integer pageIndex, Integer pageSize);

    void identityExamine(UserExamineRequest request);

    IdentityExamineInfoResponse identityExamine(Integer id);

    PageResult<UserEducationPageResponse> userEducationPage(UserPageRequest request, Integer pageIndex, Integer pageSize);

    EducationExamineInfoResponse educationExamine(Integer id);

    void educationExamine(UserExamineRequest request);

    PageResult<UserInfoPageResponse> userPage(UserInfoRequest request, Integer pageIndex, Integer pageSize);

    UserAdminInfoResponse userAdminInfo(Integer id);

    void updateUser(UserAdminInfoAddRequest request);

    void deleteUser(Integer id);

    OutlineResponse outline();

    FrontUserInfoResponse frontUserInfo(Integer id);

    List<ProportionResponse> userRegister(UserRegisterStatisticRequest request);

    void deleteFruser();

    PayMenuResponse payMenu();

    PayMenuResponse payMenuByCode(String code);

    FrUserEntity getFrUserByUnionId(String unionId);

    ViewLikeMeResponse viewLikeMePrice();

    void unlockLikeme(SeeLikeMeRequest request);
}

