package com.bdxh.wallet.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.persistence.WalletAccountRechargeMapper;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-30 19:24
 **/
@Service
public class WalletAccountRechargeServiceImpl  extends BaseService<WalletAccountRecharge> implements WalletAccountRechargeService{

    @Autowired
    private WalletAccountRechargeMapper walletAccountRechargeMapper;


    @Override
    public WalletAccountRecharge getByOrderNO(Long orderNo) {
        WalletAccountRecharge walletAccountRecharge=walletAccountRechargeMapper.getByOrderNo(orderNo);
        return walletAccountRecharge;
    }

    @Override
    public int changeRechargeLog(WalletAccountRecharge walletAccountRecharge) {
        int flag= walletAccountRechargeMapper.updateByPrimaryKeySelective(walletAccountRecharge);
        return flag;
    }

    @Override
    public PageInfo<WalletAccountRecharge> getRechargeLogPage(Map<String,Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WalletAccountRecharge> rechargeLogs = walletAccountRechargeMapper.getByCondition(param);
        PageInfo<WalletAccountRecharge> pageInfo=new PageInfo<>(rechargeLogs);
        return pageInfo;
    }

}
