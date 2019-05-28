package com.bdxh.account.service.impl;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.persistence.UserDeviceMapper;
import com.bdxh.account.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@Service
@Slf4j
public class UserDeviceServiceImpl extends BaseService<UserDevice> implements UserDeviceService {

	@Autowired
	private UserDeviceMapper userDeviceMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getUserDeviceAllCount(){
		return userDeviceMapper.getUserDeviceAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelUserDeviceInIds(List<Long> ids){
		return userDeviceMapper.delUserDeviceInIds(ids) > 0;
	}

	@Override
	public List<UserDevice> getUserDeviceAll(String schoolcode, Long groupId) {
		return userDeviceMapper.getUserDeviceAll(schoolcode,groupId);
	}

	@Override
	public UserDevice findUserDeviceByCodeOrCard(String schoolCode, String cardNumber) {
		return userDeviceMapper.findUserDeviceByCodeOrCard(schoolCode,cardNumber);
	}
}
