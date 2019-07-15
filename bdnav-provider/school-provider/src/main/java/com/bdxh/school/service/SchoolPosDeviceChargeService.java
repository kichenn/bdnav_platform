package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.ModifySchoolPosDeviceDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.entity.SchoolPosDeviceCharge;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author WanMing
* @Date 2019-07-11 10:58:36
*/
@Service
public interface SchoolPosDeviceChargeService extends IService<SchoolPosDeviceCharge> {

	/**
	 *查询所有数量
	 */
 	Integer getSchoolPosDeviceChargeAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelSchoolPosDeviceChargeInIds(List<Long> id);

	/**
	 *根据部门id查询绑定记录
	 */
    List<SchoolPosDeviceCharge> findSchoolChargeDeptByDeptId(Long id);

	/**
	 * 换绑
	 * @param modifySchoolPosDeviceDto
	 * @return
	 */
	Boolean changeSchoolPosDevice(ModifySchoolPosDeviceDto modifySchoolPosDeviceDto);

	/**
	 * 绑定收费部门和设备
	 * @param schoolPosDeviceCharge
	 * @return
	 */
    Boolean addSchoolPosDeviceCharge(SchoolPosDeviceCharge schoolPosDeviceCharge);
}
