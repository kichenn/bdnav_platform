package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.service.WalletConsumerService;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.persistence.WalletConsumerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-07-11 09:40:52
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

	/**
	 * 删除消费记录
	 *
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 */
	@Override
	public Boolean delWalletConsumer(String schoolCode, String cardNumber, Long id) {
		WalletConsumer walletConsumer = new WalletConsumer();
		walletConsumer.setSchoolCode(schoolCode);
		walletConsumer.setCardNumber(cardNumber);
		walletConsumer.setId(id);
		return walletConsumerMapper.delete(walletConsumer)>0;
	}

	/**
	 * 条件分页查询消费记录
	 * @param queryWalletConsumerDto
	 * @return
	 */
	@Override
	public PageInfo<WalletConsumerVo> findWalletConsumerByCondition(QueryWalletConsumerDto queryWalletConsumerDto) {
		PageHelper.startPage(queryWalletConsumerDto.getPageNum(),queryWalletConsumerDto.getPageSize());
		WalletConsumer walletConsumer = new WalletConsumer();
		BeanUtils.copyProperties(queryWalletConsumerDto, walletConsumer);
		List<WalletConsumer> walletConsumers = walletConsumerMapper.findWalletConsumerByCondition(walletConsumer);
		List<WalletConsumerVo> walletConsumerVos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(walletConsumers)){
			walletConsumers.forEach(consumerDetail->{
				WalletConsumerVo walletConsumerVo = new WalletConsumerVo();
				BeanUtils.copyProperties(consumerDetail, walletConsumerVo);
				walletConsumerVos.add(walletConsumerVo);
			});
		}
		PageInfo<WalletConsumerVo> pageInfo = new PageInfo(walletConsumerVos);
		return pageInfo;
	}

	/**
	 * 根据id查询单条消费记录
	 *
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	@Override
	public WalletConsumer findWalletConsumerById(String schoolCode, String cardNumber, String id) {
		return walletConsumerMapper.findWalletConsumerById(schoolCode,cardNumber,id);
	}
}
