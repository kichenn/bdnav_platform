package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.WalletRecharge;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
public interface WalletRechargeService extends IService<WalletRecharge> {

    /**
     * 查询所有数量
     */
    Integer getWalletRechargeAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelWalletRechargeInIds(List<Long> id);

    /**
     * 根据我方订单号，修改相关信息
     *
     * @param walletRecharge
     * @return
     */
    Boolean modifyWalletRechargeByOrderNo(WalletRecharge walletRecharge);

    /**
     * 我方订单号，查询充值记录信息
     * @param orderNo
     * @return
     */
    WalletRecharge findWalletRechargeByOrderNo(Long orderNo);
}
