package com.wxmblog.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.ArticleBackgroundDao;
import com.wxmblog.nostalgia.entity.ArticleBackgroundEntity;
import com.wxmblog.nostalgia.service.ArticleBackgroundService;


@Service("articleBackgroundService")
public class ArticleBackgroundServiceImpl extends ServiceImpl<ArticleBackgroundDao, ArticleBackgroundEntity> implements ArticleBackgroundService {

}
