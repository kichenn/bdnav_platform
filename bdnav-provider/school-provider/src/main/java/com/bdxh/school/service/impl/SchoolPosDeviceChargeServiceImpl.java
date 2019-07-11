package com.bdxh.school.service.impl;

import com.bdxh.school.persistence.SchoolPosDeviceChargeMapper;
import com.bdxh.school.service.SchoolPosDeviceChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolPosDeviceCharge;
import java.util.List;

/**
* @Description: 业务层实现
* @Author WanMing
* @Date 2019-07-11 10:58:36
*/
@Service
@Slf4j
public class SchoolPosDeviceChargeServiceImpl extends BaseService<SchoolPosDeviceCharge> implements SchoolPosDeviceChargeService {

	@Autowired
	private SchoolPosDeviceChargeMapper schoolPosDeviceChargeMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getSchoolPosDeviceChargeAllCount(){
		return schoolPosDeviceChargeMapper.getSchoolPosDeviceChargeAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelSchoolPosDeviceChargeInIds(List<Long> ids){
		return schoolPosDeviceChargeMapper.delSchoolPosDeviceChargeInIds(ids) > 0;
	}
}
