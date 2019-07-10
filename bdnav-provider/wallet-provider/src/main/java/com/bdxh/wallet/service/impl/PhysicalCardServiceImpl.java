package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.service.PhysicalCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.persistence.PhysicalCardMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-07-10 18:36:58
*/
@Service
@Slf4j
public class PhysicalCardServiceImpl extends BaseService<PhysicalCard> implements PhysicalCardService {

	@Autowired
	private PhysicalCardMapper physicalCardMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getPhysicalCardAllCount(){
		return physicalCardMapper.getPhysicalCardAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelPhysicalCardInIds(List<Long> ids){
		return physicalCardMapper.delPhysicalCardInIds(ids) > 0;
	}
}
