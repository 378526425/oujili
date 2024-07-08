package com.wxmblog.nostalgia.dao;

import com.wxmblog.nostalgia.common.rest.response.front.article.MessagePageResponse;
import com.wxmblog.nostalgia.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 备注
 * 
 * @author rh
 * @email rh@rh.com
 * @date 2024-05-10 23:29:26
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {

    List<MessagePageResponse> getMessagePage(Integer ownerId);
}
