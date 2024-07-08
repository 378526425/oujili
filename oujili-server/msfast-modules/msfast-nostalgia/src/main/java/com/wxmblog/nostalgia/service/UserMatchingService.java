package com.wxmblog.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.LikePageResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.MatchingResponse;
import com.wxmblog.nostalgia.common.rest.response.front.matching.SuccessPageResponse;
import com.wxmblog.nostalgia.entity.UserMatchingEntity;


/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
public interface UserMatchingService extends IService<UserMatchingEntity> {

    /**
     * @Description: 查询已经匹配的数量
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/12/12 下午2:31
     */
    Long matchingNum();

    MatchingResponse match(ChoiceRequest request);

    PageResult<LikeMePageResponse> likeMePage(Integer pageIndex, Integer pageSize);

    PageResult<SuccessPageResponse> successPage(Integer pageIndex, Integer pageSize);

    PageResult<LikePageResponse> likePage(Integer pageIndex, Integer pageSize);

    void cancelMatch(Integer id);
}

