package com.bdxh.account.service;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.common.support.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@Service
public interface UserDeviceService extends IService<UserDevice> {

	/**
	 *查询所有数量
	 */
 	Integer getUserDeviceAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelUserDeviceInIds(List<Long> id);

	/**
	 * 根据schoolcode和group_id
	 * @param schoolCode
	 * @param groupId
	 * @return
	 */
	List<UserDevice> getUserDeviceAll(String schoolCode,Long groupId);

	/**
	 * 根据code或者cardNumber查询用户设备信息
	 */
	UserDevice findUserDeviceByCodeOrCard(String schoolCode,String cardNumber);
}
