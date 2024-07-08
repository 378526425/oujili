package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.entity.ArticleCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态评论
 * 
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2024-01-31 15:23:10
 */
@Mapper
public interface ArticleCommentDao extends BaseMapper<ArticleCommentEntity> {
	
}
