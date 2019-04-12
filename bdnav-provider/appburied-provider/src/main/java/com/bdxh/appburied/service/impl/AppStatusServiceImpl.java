package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.service.AppStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.persistence.AppStatusMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Service
@Slf4j
public class AppStatusServiceImpl extends BaseService<AppStatus> implements AppStatusService {

	@Autowired
	private AppStatusMapper appStatusMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getAppStatusAllCount(){
		return appStatusMapper.getAppStatusAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelAppStatusInIds(List<Long> ids){
		return appStatusMapper.delAppStatusInIds(ids) > 0;
	}
}
