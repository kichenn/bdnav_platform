package com.bdxh.school.persistence;

import java.util.List;

import com.bdxh.school.entity.SchoolDevice;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolPosDeviceCharge;



/**
* @Description: Mapper
* @Author wanMing
* @Date 2019-07-11 10:58:36
*/
@Repository
public interface SchoolPosDeviceChargeMapper extends Mapper<SchoolPosDeviceCharge> {

	/**
	 *查询总条数
	 */
	 Integer getSchoolPosDeviceChargeAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delSchoolPosDeviceChargeInIds(@Param("ids") List<Long> ids);


    Integer changeSchoolPosDevice(@Param("deviceCharge")SchoolPosDeviceCharge deviceCharge);
}
