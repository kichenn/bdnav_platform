package com.bdxh.wallet.task;

import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 定时清理未支付的订单
 * @author: xuyuan
 * @create: 2019-01-18 17:29
 **/
@Component
@Slf4j
public class WalletClearSimpleJob implements SimpleJob {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("开始清理超时未支付的订单");
        walletAccountRechargeService.clearRechargeLog();
    }

}
