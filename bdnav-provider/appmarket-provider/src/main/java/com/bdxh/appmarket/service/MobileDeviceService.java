package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.MobileDevice;
import com.bdxh.common.support.IService;

import java.util.List;



/**
* @Description: 业务层接口
* @Date 2019-04-22 16:28:40
*/

public interface MobileDeviceService extends IService<MobileDevice> {

	//查询列表信息根据条件
	List<MobileDevice> findMobileDeviceInCondition(MobileDevice mobileDevice);

	//查询所有数量
	Integer getMobileDeviceAllCount();

	//新增方法
	Boolean addMobileDevice(MobileDevice mobileDevice);

	//修改方法
	Boolean modifyMobileDevice(MobileDevice mobileDevice);

	//删除方法
	Boolean delMobileDeviceById(Long id);

	//批量删除方法
	Boolean batchDelMobileDeviceInIds(List<Long> id);

	//根据ID查询对象的方法
	public MobileDevice findMobileDeviceById(Long id);
}
