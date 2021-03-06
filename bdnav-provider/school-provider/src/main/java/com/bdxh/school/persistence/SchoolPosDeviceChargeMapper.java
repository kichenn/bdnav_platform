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

	/**
	 * 换绑
	 * @param deviceCharge
	 * @return
	 */
    Integer changeSchoolPosDevice(@Param("deviceCharge")SchoolPosDeviceCharge deviceCharge);

	/**
	 * 解绑消费机和部门关系
	 * @param deviceId
	 * @return
	 */
	Integer delSchoolPosDeviceCharge(@Param("deviceId")Long deviceId);

	/**
	 * 查询改学校的消费机名称
	 * @param schoolCode
	 * @return
	 */
    List<String> queryPosNum(@Param("schoolCode")String schoolCode);
}
