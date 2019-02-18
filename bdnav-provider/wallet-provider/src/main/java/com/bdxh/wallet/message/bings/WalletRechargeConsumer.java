package com.bdxh.wallet.message.bings;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.XianAddBlanceDto;
import com.bdxh.onecard.feign.XianCardControllerClient;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.message.stream.WalletRechargeSink;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
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
@EnableBinding(WalletRechargeSink.class)
@Slf4j
public class WalletRechargeConsumer {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @Autowired
    private XianCardControllerClient xianCardControllerClient;

    @StreamListener(WalletRechargeSink.INPUT)
    public void reciveWalletRecharge(Message<String> message){
        MessageHeaders headers = message.getHeaders();
        MessageExt messageExt = headers.get("ORIGINAL_ROCKETMQ_MESSAGE", MessageExt.class);
        int reconsumeTimes = messageExt.getReconsumeTimes();
        //默认重试16次 4次之后不再处理 定时任务补偿
        if (reconsumeTimes<5){
            String recharge=message.getPayload();
            log.info("收到一卡通充值消息：{}",recharge);
            WalletAccountRecharge walletAccountRecharge = JSON.parseObject(recharge, WalletAccountRecharge.class);
            //一卡通充值
            XianAddBlanceDto xianAddBlanceDto = new XianAddBlanceDto();
            xianAddBlanceDto.setSchoolCode(walletAccountRecharge.getSchoolCode());
            xianAddBlanceDto.setCardNumber(walletAccountRecharge.getCardNumber());
            xianAddBlanceDto.setMoney(walletAccountRecharge.getRechargeMoney());
            xianAddBlanceDto.setOrderNo(String.valueOf(walletAccountRecharge.getOrderNo()));
            xianAddBlanceDto.setUserName(walletAccountRecharge.getUserName());
            Wrapper wrapper = xianCardControllerClient.addBalance(xianAddBlanceDto);
            log.info("订单号{}一卡通充值返回结果：",walletAccountRecharge.getOrderNo(),JSON.toJSON(wrapper));
            Preconditions.checkArgument(wrapper.getCode()==200,"一卡通充值失败");
            try {
                String acceptseq = (String) wrapper.getResult();
                WalletAccountRecharge walletAccountRechargeNew = new WalletAccountRecharge();
                walletAccountRechargeNew.setId(walletAccountRecharge.getId());
                walletAccountRechargeNew.setAcceptseq(acceptseq);
                walletAccountRechargeService.update(walletAccountRechargeNew);
            }catch (Exception e){
                e.printStackTrace();
                log.error("订单号："+xianAddBlanceDto.getOrderNo()+"更新流水号失败",e.getStackTrace());
            }
        }
    }

}
