package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.SuccessPageResponse;
import com.wxmblog.nostalgia.entity.UserMatchingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Mapper
public interface UserMatchingDao extends BaseMapper<UserMatchingEntity> {

    List<LikeMePageResponse> getLikeMePage(Integer userId);

    List<SuccessPageResponse> getSuccessPage(Integer userId);

    List<LikePageResponse> getLikePage(Integer userId);
}
