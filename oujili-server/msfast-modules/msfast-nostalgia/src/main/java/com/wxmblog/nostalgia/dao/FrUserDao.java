package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.common.rest.request.admin.statistic.UserRegisterStatisticRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserInfoRequest;
import com.wxmblog.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxmblog.nostalgia.common.rest.response.admin.statistic.ProportionResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.UserEducationPageResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.UserIdentityPageResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.UserInfoPageResponse;
import com.wxmblog.nostalgia.common.rest.response.admin.user.UserPageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.LikeResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.RecommendUserInfoResponse;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Mapper
public interface FrUserDao extends BaseMapper<FrUserEntity> {

    List<RecommendUserInfoResponse> getRecommendUserInfo(Map<String, Object> param);

    LikeResponse getPersonalLike(Integer userId);

    List<UserPageResponse> getExaminePage(UserPageRequest request);

    List<UserIdentityPageResponse> getIdentityPage(UserPageRequest request);

    List<UserEducationPageResponse> getUserEducationPage(UserPageRequest request);

    List<UserInfoPageResponse> getUserInfoPage(UserInfoRequest request);

    List<ProportionResponse> getGenderPie();

    List<ProportionResponse> getCityBar();

    List<ProportionResponse> getUserRegisterStatistic(UserRegisterStatisticRequest request);
}
