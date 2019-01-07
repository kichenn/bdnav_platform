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
}