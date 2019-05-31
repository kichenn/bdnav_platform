package com.bdxh.servicepermit.service.impl;


import com.bdxh.common.support.BaseService;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.persistence.ServiceRoleMapper;
import com.bdxh.servicepermit.service.ServiceRoleService;
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
public class ServiceRoleServiceImpl extends BaseService<ServiceRole> implements ServiceRoleService {

	@Autowired
	private ServiceRoleMapper serviceRoleMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getServiceRoleAllCount(){
		return serviceRoleMapper.getServiceRoleAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelServiceRoleInIds(List<Long> ids){
		return serviceRoleMapper.delServiceRoleInIds(ids) > 0;
	}
}
