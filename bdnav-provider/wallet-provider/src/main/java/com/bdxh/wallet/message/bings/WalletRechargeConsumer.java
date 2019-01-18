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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

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
        String recharge=message.getPayload();
        log.info("收到消息："+recharge);
        WalletAccountRecharge walletAccountRecharge = JSON.parseObject(recharge, WalletAccountRecharge.class);
        //一卡通充值
        XianAddBlanceDto xianAddBlanceDto = new XianAddBlanceDto();
        xianAddBlanceDto.setSchoolCode(walletAccountRecharge.getSchoolCode());
        xianAddBlanceDto.setCardNumber(walletAccountRecharge.getCardNumber());
        xianAddBlanceDto.setMoney(walletAccountRecharge.getRechargeMoney());
        xianAddBlanceDto.setOrderNo(String.valueOf(walletAccountRecharge.getOrderNo()));
        xianAddBlanceDto.setUserName(walletAccountRecharge.getUserName());
        Wrapper wrapper = xianCardControllerClient.addBalance(xianAddBlanceDto);
        log.info("一卡通返回结果："+JSON.toJSON(wrapper));
        //状态500时 100009代表订单号重复，表示已经充值过 貌似没有同步数据时对方也是返回此code值，挺6的
        Preconditions.checkArgument(wrapper.getCode()==200|| (wrapper.getCode()==500&&StringUtils.equals(String.valueOf(wrapper.getMessage()),"100009")),"一卡通充值失败");
        //更新流水号
        if (wrapper.getCode()==200){
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
