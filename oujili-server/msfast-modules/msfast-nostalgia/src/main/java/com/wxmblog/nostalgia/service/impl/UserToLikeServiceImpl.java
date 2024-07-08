package com.wxmblog.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxmblog.nostalgia.dao.UserToLikeDao;
import com.wxmblog.nostalgia.entity.UserToLikeEntity;
import com.wxmblog.nostalgia.service.UserToLikeService;


@Service("userToLikeService")
public class UserToLikeServiceImpl extends ServiceImpl<UserToLikeDao, UserToLikeEntity> implements UserToLikeService {

}
