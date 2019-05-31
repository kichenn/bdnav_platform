package com.bdxh.school.service.impl;

import com.bdxh.school.service.SchoolOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.persistence.SchoolOrgMapper;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-31 14:06:08
*/
@Service
@Slf4j
public class SchoolOrgServiceImpl extends BaseService<SchoolOrg> implements SchoolOrgService {

	@Autowired
	private SchoolOrgMapper schoolOrgMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSchoolOrgAllCount(){
		return schoolOrgMapper.getSchoolOrgAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSchoolOrgInIds(List<Long> ids){
		return schoolOrgMapper.delSchoolOrgInIds(ids) > 0;
	}
}
