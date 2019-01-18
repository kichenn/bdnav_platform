package com.bdxh.wallet.persistence;

import com.bdxh.wallet.entity.WalletAccountRecharge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface WalletAccountRechargeMapper extends Mapper<WalletAccountRecharge> {
    /**
     * 根据订单号查询充值记录
     * @param orderNo
     * @return
     */
    WalletAccountRecharge getByOrderNo(@Param("orderNo") Long orderNo);
    /**
     * 根据条件查询充值列表
     * @param param
     * @return
     */
    List<WalletAccountRecharge> getByCondition(Map<String,Object> param);
    /**
     * 支付成功后更改状态为支付中
     * @param status
     * @return
     */
    int updatePaying(@Param("orderNo") Long orderNo, @Param("status") Byte status);

    /**
     * 查询通知超时的数据
     * @param param
     * @return
     */
    List<WalletAccountRecharge> getPayingDataForTask(Map<String,Object> param);

    /**
     * 查询一卡通充值超时的数据
     * @param param
     * @return
     */
    List<WalletAccountRecharge> getSerailNoNullForTask(Map<String,Object> param);

    /**
     * 定时清理12小时未支付的订单
     */
    void clearRechargeLog();

}