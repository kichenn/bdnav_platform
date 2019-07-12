package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.WalletAccount;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
public interface WalletAccountService extends IService<WalletAccount> {

    /**
     * 查询所有数量
     */
    Integer getWalletAccountAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelWalletAccountInIds(List<Long> id);

    /**
     * 卡号加学校id查询钱包信息
     *
     * @param cardNumber
     * @param schoolCode
     * @return
     */
    WalletAccount findWalletByCardNumberAndSchoolCode(String cardNumber, String schoolCode);

    /**
     * 设置支付密码
     */
    Boolean setPayPwd(String cardNumber, String schoolCode, String payPwd);

    /**
     * 设置免密支付
     *
     * @return
     */
    Boolean noPwdPay(WalletAccount walletAccount);
}
