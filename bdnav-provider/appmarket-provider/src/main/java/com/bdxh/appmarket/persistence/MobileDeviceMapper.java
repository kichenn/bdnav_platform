package com.bdxh.appmarket.persistence;

import java.util.List;

import com.bdxh.appmarket.entity.MobileDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
* @Description: 持久化
* @Date 2019-04-22 16:28:40
*/
@Repository
public interface MobileDeviceMapper extends Mapper<MobileDevice> {
	//查询所有数量
	public Integer getMobileDeviceAllCount();
	//批量删除方法
	public Integer delMobileDeviceInIds(@Param("ids") List<Long> ids);
}
