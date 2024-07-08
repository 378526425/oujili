package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.entity.UserToLikeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户点赞关联
 * 
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-02-04 11:37:30
 */
@Mapper
public interface UserToLikeDao extends BaseMapper<UserToLikeEntity> {
	
}
