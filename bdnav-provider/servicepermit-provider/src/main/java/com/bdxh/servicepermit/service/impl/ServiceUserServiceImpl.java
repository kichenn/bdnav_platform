package com.bdxh.servicepermit.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.service.ServiceUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.persistence.ServiceUserMapper;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层实现
* @Date 2019-04-26 10:26:58
*/
@Service
@Slf4j
public class ServiceUserServiceImpl extends BaseService<ServiceUser> implements ServiceUserService {

	@Autowired
	private ServiceUserMapper serviceUserMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getServiceUsedAllCount(){
		return serviceUserMapper.getServiceUserAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelServiceUsedInIds(List<Long> ids){
		return serviceUserMapper.delServiceUserInIds(ids) > 0;
	}

	@Override
	public PageInfo<ServiceUser> getServiceByCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<ServiceUser> orders = serviceUserMapper.getServiceByCondition(param);
		PageInfo<ServiceUser> pageInfo = new PageInfo<>(orders);
		return pageInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteByServiceId(String SchoolCode, Long cardNumber, Long id) {
		return serviceUserMapper.deleteByServiceId(SchoolCode,cardNumber,id)>0;
	}



}
