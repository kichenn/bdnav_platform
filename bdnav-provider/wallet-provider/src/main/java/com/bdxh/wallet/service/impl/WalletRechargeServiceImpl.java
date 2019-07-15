package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.service.WalletRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.persistence.WalletRechargeMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Service
@Slf4j
public class WalletRechargeServiceImpl extends BaseService<WalletRecharge> implements WalletRechargeService {

	@Autowired
	private WalletRechargeMapper walletRechargeMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getWalletRechargeAllCount(){
		return walletRechargeMapper.getWalletRechargeAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelWalletRechargeInIds(List<Long> ids){
		return walletRechargeMapper.delWalletRechargeInIds(ids) > 0;
	}
}