package com.bdxh.school.service.impl;

import com.bdxh.school.dto.ModifySchoolPosDeviceDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.enums.DeviceStatusEnum;
import com.bdxh.school.persistence.SchoolDeviceMapper;
import com.bdxh.school.persistence.SchoolPosDeviceChargeMapper;
import com.bdxh.school.service.SchoolPosDeviceChargeService;
import org.springframework.beans.BeanUtils;
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

	@Autowired
	private SchoolDeviceMapper schoolDeviceMapper;

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

	/**
	 * 根据部门id查询绑定记录
	 *
	 * @param id
	 */
	@Override
	public List<SchoolPosDeviceCharge> findSchoolChargeDeptByDeptId(Long id) {
		SchoolPosDeviceCharge deviceCharge = new SchoolPosDeviceCharge();
		deviceCharge.setChargeDeptId(id);
		return schoolPosDeviceChargeMapper.select(deviceCharge);
	}

	/**
	 * 换绑pos机到其他部门
	 * @param modifySchoolPosDeviceDto
	 * @return
	 */
	@Override
	public Boolean changeSchoolPosDevice(ModifySchoolPosDeviceDto modifySchoolPosDeviceDto) {
		SchoolPosDeviceCharge deviceCharge = new SchoolPosDeviceCharge();
		BeanUtils.copyProperties(modifySchoolPosDeviceDto,deviceCharge);
		return schoolPosDeviceChargeMapper.changeSchoolPosDevice(deviceCharge)>0;
	}

	/**
	 * 绑定收费部门和设备
	 *
	 * @param schoolPosDeviceCharge
	 * @return
	 */
	@Override
	public Boolean addSchoolPosDeviceCharge(SchoolPosDeviceCharge schoolPosDeviceCharge) {
		Boolean result = schoolPosDeviceChargeMapper.insertSelective(schoolPosDeviceCharge) > 0;
		if(result){
			//修改设备状态
			SchoolDevice schoolDevice = new SchoolDevice();
			schoolDevice.setId(schoolPosDeviceCharge.getDeviceId());
			schoolDevice.setDeviceStatus(DeviceStatusEnum.NORMAL_KEY);
			schoolDeviceMapper.updateByPrimaryKeySelective(schoolDevice);
		}
		return result;
	}


}
