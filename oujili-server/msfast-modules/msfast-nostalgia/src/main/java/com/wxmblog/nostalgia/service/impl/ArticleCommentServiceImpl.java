package com.wxmblog.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.ArticleCommentDao;
import com.wxmblog.nostalgia.entity.ArticleCommentEntity;
import com.wxmblog.nostalgia.service.ArticleCommentService;


@Service("articleCommentService")
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentDao, ArticleCommentEntity> implements ArticleCommentService {

}
