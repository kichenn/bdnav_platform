package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.service.WalletConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.persistence.WalletConsumerMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-07-10 18:36:58
*/
@Service
@Slf4j
public class WalletConsumerServiceImpl extends BaseService<WalletConsumer> implements WalletConsumerService {

	@Autowired
	private WalletConsumerMapper walletConsumerMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getWalletConsumerAllCount(){
		return walletConsumerMapper.getWalletConsumerAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelWalletConsumerInIds(List<Long> ids){
		return walletConsumerMapper.delWalletConsumerInIds(ids) > 0;
	}
}
