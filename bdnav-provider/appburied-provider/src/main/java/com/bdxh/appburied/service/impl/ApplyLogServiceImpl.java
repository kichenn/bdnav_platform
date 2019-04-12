package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.service.ApplyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.persistence.ApplyLogMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Service
@Slf4j
public class ApplyLogServiceImpl extends BaseService<ApplyLog> implements ApplyLogService {

	@Autowired
	private ApplyLogMapper applyLogMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getApplyLogAllCount(){
		return applyLogMapper.getApplyLogAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelApplyLogInIds(List<Long> ids){
		return applyLogMapper.delApplyLogInIds(ids) > 0;
	}
}
