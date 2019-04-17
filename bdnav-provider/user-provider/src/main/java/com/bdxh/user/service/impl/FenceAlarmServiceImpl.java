package com.bdxh.user.service.impl;

import com.bdxh.user.entity.FenceAlarm;
import com.bdxh.user.persistence.FenceAlarmMapper;
import com.bdxh.user.service.FenceAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
@Slf4j
public class FenceAlarmServiceImpl extends BaseService<FenceAlarm> implements FenceAlarmService {

	@Autowired
	private FenceAlarmMapper fenceAlarmMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getFenceAlarmAllCount(){
		return fenceAlarmMapper.getFenceAlarmAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelFenceAlarmInIds(List<Long> ids){
		return fenceAlarmMapper.delFenceAlarmInIds(ids) > 0;
	}
}
