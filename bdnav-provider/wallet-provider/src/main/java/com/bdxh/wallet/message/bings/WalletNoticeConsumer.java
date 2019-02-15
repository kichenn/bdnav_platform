package com.bdxh.wallet.message.bings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.enums.PayCardStatusEnum;
import com.bdxh.common.base.enums.WxPayNoticeResultEnum;
import com.bdxh.wallet.message.stream.WalletNoticeSink;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @description: 微信支付回调消费者
 * @author: xuyuan
 * @create: 2019-01-16 10:54
 **/
@EnableBinding(WalletNoticeSink.class)
@Slf4j
public class WalletNoticeConsumer {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @StreamListener(WalletNoticeSink.INPUT)
    public void reciveWalletNotice(Message<String> message){
        MessageHeaders headers = message.getHeaders();
        MessageExt messageExt = headers.get("ORIGINAL_ROCKETMQ_MESSAGE", MessageExt.class);
        int reconsumeTimes = messageExt.getReconsumeTimes();
        //4次之后不再处理 定时任务补偿
        if (reconsumeTimes<5){
            String notice=message.getPayload();
            log.info("收到一卡通充值消息：{}",notice);
            JSONObject jsonObject = JSON.parseObject(notice);
            Long orderNo = jsonObject.getLong("orderNo");
            String thirdOrderNo=jsonObject.getString("thirdOrderNo");
            String resultCode = jsonObject.getString("resultCode");
            if (orderNo!=null&&StringUtils.isNotEmpty(resultCode)){
                if (StringUtils.equals(resultCode, WxPayNoticeResultEnum.SUCCESS.name())){
                    walletAccountRechargeService.rechargeWallet(orderNo,thirdOrderNo, PayCardStatusEnum.RECHARGE_SUCCESS.getCode());
                }
                if (StringUtils.equals(resultCode, WxPayNoticeResultEnum.FAIL.name())){
                    walletAccountRechargeService.rechargeWallet(orderNo,thirdOrderNo, PayCardStatusEnum.RECHARGE_FAIL.getCode());
                }
            }
        }
    }

}
