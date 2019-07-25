package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.service.WalletAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.persistence.WalletAccountMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
@Slf4j
public class WalletAccountServiceImpl extends BaseService<WalletAccount> implements WalletAccountService {

    @Autowired
    private WalletAccountMapper walletAccountMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getWalletAccountAllCount() {
        return walletAccountMapper.getWalletAccountAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelWalletAccountInIds(List<Long> ids) {
        return walletAccountMapper.delWalletAccountInIds(ids) > 0;
    }

    /**
     * 卡号加学校id查询钱包信息
     *
     * @param cardNumber
     * @param schoolCode
     * @return
     */
    @Override
    public WalletAccount findWalletByCardNumberAndSchoolCode(String cardNumber, String schoolCode) {
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setCardNumber(cardNumber);
        walletAccount.setSchoolCode(schoolCode);
        return walletAccountMapper.selectOne(walletAccount);
    }

    /**
     * 设置支付密码
     */
    @Override
    public Boolean setPayPwd(String cardNumber, String schoolCode, String payPwd) {
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setCardNumber(cardNumber);
        walletAccount.setSchoolCode(schoolCode);
        walletAccount.setPayPassword(payPwd);
        walletAccount.setUpdateDate(new Date());
        return walletAccountMapper.modifyWalletBySchoolCodeAndCardNumber(walletAccount) > 0;
    }

    /**
     * 设置免密支付
     *
     * @return
     */
    @Override
    public Boolean noPwdPay(WalletAccount walletAccount) {
        return walletAccountMapper.modifyWalletBySchoolCodeAndCardNumber(walletAccount) > 0;
    }

    /**
     * 账户充值
     */
    @Override
    public Boolean walletAccountRecharge(String cardNumber, String schoolCode, BigDecimal amount) {
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setSchoolCode(schoolCode);
        walletAccount.setCardNumber(cardNumber);
        walletAccount.setAmount(amount);
        return walletAccountMapper.modifyWalletBySchoolCodeAndCardNumber(walletAccount) > 0;
    }

    @Override
    public WalletAccount findWalletAccountById(String schoolCode, String cardNumber, Long id) {
        return walletAccountMapper.findWalletAccountById(schoolCode,cardNumber,id);
    }

    @Override
    public PageInfo<WalletAccount> findWalletAccountInCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletAccount> walletAccounts = walletAccountMapper.findWalletAccountInCondition(param);
        return new PageInfo(walletAccounts);
    }

    @Override
    public Boolean delWalletAccount(String schoolCode, String cardNumber, Long id) {
        return walletAccountMapper.delWalletAccount(schoolCode, cardNumber, id) > 0;
    }

}
