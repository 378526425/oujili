package com.wxmblog.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.CommentReplyDao;
import com.wxmblog.nostalgia.entity.CommentReplyEntity;
import com.wxmblog.nostalgia.service.CommentReplyService;


@Service("commentReplyService")
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyDao, CommentReplyEntity> implements CommentReplyService {

}
