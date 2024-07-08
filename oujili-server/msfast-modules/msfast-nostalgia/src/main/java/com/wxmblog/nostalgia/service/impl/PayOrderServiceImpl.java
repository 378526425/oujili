package com.wxmblog.nostalgia.service.impl;

import com.wxmblog.nostalgia.dao.PayOrderDao;
import com.wxmblog.nostalgia.entity.PayOrderEntity;
import com.wxmblog.nostalgia.service.PayOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("payOrderService")
public class PayOrderServiceImpl extends ServiceImpl<PayOrderDao, PayOrderEntity> implements PayOrderService {

}
