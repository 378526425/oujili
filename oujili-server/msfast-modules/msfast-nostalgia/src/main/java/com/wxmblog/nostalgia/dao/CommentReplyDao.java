package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.entity.CommentReplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论回复
 * 
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@Mapper
public interface CommentReplyDao extends BaseMapper<CommentReplyEntity> {
	
}
