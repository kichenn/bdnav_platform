package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.dto.WalletPayAppOrderDto;
import com.bdxh.wallet.dto.WalletPayJsOrderDto;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.vo.WalletAppOrderVo;
import com.bdxh.wallet.vo.WalletJsOrderVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
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

    /**
     * 支付成功后更改状态为支付中
     * @param status
     */
    void updatePaying(Long orderNo,Byte status);

    /**
     * APP支付下单
     * @param walletPayAppOrderDto
     */
    WalletAppOrderVo appOrder(WalletPayAppOrderDto walletPayAppOrderDto);

    /**
     * JS支付下单
     * @param walletPayJsOrderDto
     */
    WalletJsOrderVo jsOrder(WalletPayJsOrderDto walletPayJsOrderDto);

    /**
     * 充值一卡通
     * @param orderNo
     * @param thirdOrderNo
     * @param status
     */
    void rechargeWallet(Long orderNo,String thirdOrderNo,Byte status);

    /**
     * 查询通知超时的数据
     * @param param
     */
    List<WalletAccountRecharge> queryPayingDataForTask(Map<String,Object> param);

    /**
     * 查询一卡通充值超时的数据
     * @param param
     * @return
     */
    List<WalletAccountRecharge> querySerailNoNullForTask(Map<String,Object> param);

    /**
     * 定时清理12小时未支付的订单
     */
    void clearRechargeLog();

}
