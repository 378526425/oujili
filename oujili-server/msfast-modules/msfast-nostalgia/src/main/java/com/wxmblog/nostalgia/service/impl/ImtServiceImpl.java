package com.wxmblog.nostalgia.service.impl;

import com.wxmblog.base.websocket.service.impl.IImtServiceImpl;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-10 11:08
 **/
@Service
public class ImtServiceImpl extends IImtServiceImpl {

    @Override
    public void read(Channel channel, String message) {

    }
}
