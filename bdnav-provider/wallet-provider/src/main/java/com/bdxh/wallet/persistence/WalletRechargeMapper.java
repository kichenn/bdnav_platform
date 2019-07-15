package com.bdxh.wallet.persistence;

import java.util.List;

import com.bdxh.wallet.entity.WalletRecharge;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Repository
public interface WalletRechargeMapper extends Mapper<WalletRecharge> {

    /**
     * 查询总条数
     */
    Integer getWalletRechargeAllCount();

    /**
     * 批量删除方法
     */
    Integer delWalletRechargeInIds(@Param("ids") List<Long> ids);

    /**
     * 根据我方订单号，修改相关信息
     *
     * @param walletRecharge
     * @return
     */
    Integer modifyWalletRechargeByOrderNo(@Param("walletRecharge") WalletRecharge walletRecharge);
}
