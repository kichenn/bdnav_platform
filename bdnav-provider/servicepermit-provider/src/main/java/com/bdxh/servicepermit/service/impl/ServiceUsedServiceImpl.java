package com.bdxh.servicepermit.service.impl;

import com.bdxh.servicepermit.service.ServiceUsedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceUsed;
import com.bdxh.servicepermit.persistence.ServiceUsedMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Service
@Slf4j
public class ServiceUsedServiceImpl extends BaseService<ServiceUsed> implements ServiceUsedService {

	@Autowired
	private ServiceUsedMapper serviceUsedMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getServiceUsedAllCount(){
		return serviceUsedMapper.getServiceUsedAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelServiceUsedInIds(List<Long> ids){
		return serviceUsedMapper.delServiceUsedInIds(ids) > 0;
	}
}
