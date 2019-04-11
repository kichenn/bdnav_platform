package com.bdxh.school.service.impl;

import com.bdxh.school.service.SchoolFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.persistence.SchoolFenceMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-04-11 09:56:14
*/
@Service
@Slf4j
public class SchoolFenceServiceImpl extends BaseService<SchoolFence> implements SchoolFenceService {

	@Autowired
	private SchoolFenceMapper schoolFenceMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSchoolFenceAllCount(){
		return schoolFenceMapper.getSchoolFenceAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSchoolFenceInIds(List<Long> ids){
		return schoolFenceMapper.delSchoolFenceInIds(ids) > 0;
	}
}
