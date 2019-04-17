package com.bdxh.user.service.impl;

import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.persistence.VisitLogsMapper;
import com.bdxh.user.service.VisitLogsService;
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
public class VisitLogsServiceImpl extends BaseService<VisitLogs> implements VisitLogsService {

	@Autowired
	private VisitLogsMapper visitLogsMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getVisitLogsAllCount(){
		return visitLogsMapper.getVisitLogsAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelVisitLogsInIds(List<Long> ids){
		return visitLogsMapper.delVisitLogsInIds(ids) > 0;
	}
}
