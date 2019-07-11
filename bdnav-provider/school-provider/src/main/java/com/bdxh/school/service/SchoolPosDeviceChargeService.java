package com.bdxh.school.service;

import com.bdxh.common.support.IService;
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

}
