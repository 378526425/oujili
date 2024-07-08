package com.wxmblog.nostalgia.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxmblog.base.auth.service.MsfConfigService;
import com.wxmblog.base.common.utils.MsfCommonTool;
import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.pay.common.rest.response.NotifyUrlData;
import com.wxmblog.base.pay.common.rest.response.PayOrderData;
import com.wxmblog.base.pay.service.impl.IWxPayServiceImpl;
import com.wxmblog.nostalgia.common.enums.user.PayOrderStatusEnum;
import com.wxmblog.nostalgia.common.enums.user.SysConfigCodeEnum;
import com.wxmblog.nostalgia.common.rest.request.payment.PayRequest;
import com.wxmblog.nostalgia.common.rest.response.front.payment.PayMoneyResponse;
import com.wxmblog.nostalgia.entity.FrUserEntity;
import com.wxmblog.nostalgia.entity.PayOrderEntity;
import com.wxmblog.nostalgia.service.FrUserService;
import com.wxmblog.nostalgia.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WxPayServiceImpl extends IWxPayServiceImpl<PayRequest> {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    PayOrderService payOrderService;

    @Autowired
    FrUserService frUserService;

    @Transactional
    @Override
    public PayOrderData wxAppletPay(PayRequest request) {

        String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
        if (StringUtils.isNotBlank(menuValue)) {
            List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
            PayMoneyResponse payMoneyResponse = moneyResponseList.stream().filter(p -> p.getPrice() != null && p.getPrice().equals(request.getProductNo())).findFirst().orElse(null);
            if (payMoneyResponse != null) {
                PayOrderData payOrderData = new PayOrderData();
                payOrderData.setBody("思君币");
                String outTradeNo = MsfCommonTool.UUID();
                payOrderData.setOutTradeNo(outTradeNo);
                payOrderData.setTotalFee(1);//payMoneyResponse.getPrice() * 100);
                Map<String, Object> attach = new HashMap<>();
                attach.put("userId", TokenUtils.getOwnerId());
                payOrderData.setAttach(JSON.toJSONString(attach));

                //保存订单
                PayOrderEntity payOrderEntity = new PayOrderEntity();
                payOrderEntity.setOutTradeNo(outTradeNo);
                payOrderEntity.setStatus(PayOrderStatusEnum.PRE_PAY);
                payOrderEntity.setTitle(payOrderData.getBody());
                payOrderEntity.setTotalFee(payMoneyResponse.getPrice());
                payOrderEntity.setUserId(TokenUtils.getOwnerId());
                payOrderEntity.setProductNo(request.getProductNo());
                payOrderService.save(payOrderEntity);

                return payOrderData;
            }
        }
        return null;
    }

    @Transactional
    @Override
    public void appletNotifyUrl(NotifyUrlData request) {
        notifyOrder(request);
    }

    private void notifyOrder(NotifyUrlData request) {

        log.info("回调方法{}", JSONObject.toJSONString(request));
        Wrapper<PayOrderEntity> queryWrapper = new QueryWrapper<PayOrderEntity>().lambda()
                .eq(PayOrderEntity::getOutTradeNo, request.getOutTradeNo());
        PayOrderEntity payOrderEntity = payOrderService.getBaseMapper().selectOne(queryWrapper);
        if (payOrderEntity != null && PayOrderStatusEnum.PRE_PAY.equals(payOrderEntity.getStatus())) {

            String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
            if (StringUtils.isNotBlank(menuValue)) {
                List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
                PayMoneyResponse payMoneyResponse = moneyResponseList.stream().filter(p -> p.getPrice() != null && p.getPrice().equals(payOrderEntity.getProductNo())).findFirst().orElse(null);
                if (payMoneyResponse != null) {
                    String attach = request.getAttach();
                    JSONObject jsonObject = JSONObject.parseObject(attach);
                    Integer userId = jsonObject.getInteger("userId");
                    log.info("回调方法,userId:{},金币数量{}", userId, payMoneyResponse.getAmount());
                    FrUserEntity frUserEntity = frUserService.getById(userId);
                    if (frUserEntity != null && frUserEntity.getGoldBalance() != null) {
                        frUserEntity.setGoldBalance(frUserEntity.getGoldBalance() + payMoneyResponse.getAmount());
                        log.info("回调方法,修改用户信息{}", JSON.toJSONString(frUserEntity));
                        frUserService.saveOrUpdate(frUserEntity);
                    }
                } else {
                    log.info("回调方法,没有查到菜单");
                }
            }
            payOrderEntity.setStatus(PayOrderStatusEnum.SUCCESS);
            payOrderService.saveOrUpdate(payOrderEntity);
        }
    }

    @Override
    public PayOrderData wxPublicPay(PayRequest request) {
        String menuValue = msfConfigService.getValueByCode(SysConfigCodeEnum.payMenuList.name());
        if (StringUtils.isNotBlank(menuValue)) {
            List<PayMoneyResponse> moneyResponseList = JSON.parseArray(menuValue, PayMoneyResponse.class);
            PayMoneyResponse payMoneyResponse = moneyResponseList.stream().filter(p -> p.getPrice() != null && p.getPrice().equals(request.getProductNo())).findFirst().orElse(null);
            if (payMoneyResponse != null) {
                PayOrderData payOrderData = new PayOrderData();
                payOrderData.setBody("思君币");
                String outTradeNo = MsfCommonTool.UUID();
                payOrderData.setOutTradeNo(outTradeNo);
                payOrderData.setTotalFee(1);//payMoneyResponse.getPrice() * 100);
                Map<String, Object> attach = new HashMap<>();
                attach.put("userId", request.getUserId());
                payOrderData.setAttach(JSON.toJSONString(attach));

                //保存订单
                PayOrderEntity payOrderEntity = new PayOrderEntity();
                payOrderEntity.setOutTradeNo(outTradeNo);
                payOrderEntity.setStatus(PayOrderStatusEnum.PRE_PAY);
                payOrderEntity.setTitle(payOrderData.getBody());
                payOrderEntity.setTotalFee(payMoneyResponse.getPrice());
                payOrderEntity.setUserId(request.getUserId());
                payOrderEntity.setProductNo(request.getProductNo());
                payOrderService.save(payOrderEntity);

                return payOrderData;
            }
        }
        return null;
    }

    @Override
    public void publicNotifyUrl(NotifyUrlData request) {
        notifyOrder(request);
    }
}
