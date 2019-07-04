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
	 * 根据条件查询设备号 只返回clientId
	 * @param schoolCode
	 * @param ids
	 * @return
	 */
	List<String> getUserDeviceAll(String schoolCode,String ids);

	/**
	 * 根据code或者cardNumber查询用户设备信息
	 */
	UserDevice findUserDeviceByCodeOrCard(String schoolCode,String cardNumber);

	//根据school查询学校下所有设备
	List<UserDevice> findUserDeviceList(String schoolCode);
}
