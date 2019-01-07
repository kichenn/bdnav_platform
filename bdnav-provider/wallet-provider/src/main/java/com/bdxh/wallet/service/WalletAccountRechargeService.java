package com.bdxh.wallet.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-30 19:18
 **/
public interface WalletAccountRechargeService extends IService<WalletAccountRecharge> {
    /**
     * 根据订单号查询充值记录
     * @param orderNo
     * @return
     */
    WalletAccountRecharge getByOrderNO(Long orderNo);
    /**
     * 根据id更改充值记录
     * @return
     */
    int changeRechargeLog(WalletAccountRecharge walletAccountRecharge);
    /**
     * 根据条件分页查询充值记录
     * @return
     */
    PageInfo<WalletAccountRecharge> getRechargeLogPage(Map<String,Object> param, int pageNum, int pageSize);
}
