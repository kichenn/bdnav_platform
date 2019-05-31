package com.bdxh.servicepermit.service.impl;


import com.bdxh.common.support.BaseService;
import com.bdxh.servicepermit.entity.ServiceRolePermit;
import com.bdxh.servicepermit.persistence.ServiceRolePermitMapper;
import com.bdxh.servicepermit.service.ServiceRolePermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-31 11:36:26
*/
@Service
@Slf4j
public class ServiceRolePermitServiceImpl extends BaseService<ServiceRolePermit> implements ServiceRolePermitService {

	@Autowired
	private ServiceRolePermitMapper serviceRolePermitMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getServiceRolePermitAllCount(){
		return serviceRolePermitMapper.getServiceRolePermitAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelServiceRolePermitInIds(List<Long> ids){
		return serviceRolePermitMapper.delServiceRolePermitInIds(ids) > 0;
	}
}
