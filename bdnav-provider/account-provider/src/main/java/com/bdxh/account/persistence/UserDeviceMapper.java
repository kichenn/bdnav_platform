package com.bdxh.account.persistence;

import java.util.List;


import com.bdxh.account.entity.UserDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@Repository
public interface UserDeviceMapper extends Mapper<UserDevice> {

	/**
	 *查询总条数
	 */
	 Integer getUserDeviceAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delUserDeviceInIds(@Param("ids") List<Long> ids);

	/**
	 * 根据schoolcode和所属部门下的列表
	 * @param schoolCode
	 * @param groupId
	 * @return
	 */
	 List<UserDevice> getUserDeviceAll(String schoolCode,Long groupId);

}
