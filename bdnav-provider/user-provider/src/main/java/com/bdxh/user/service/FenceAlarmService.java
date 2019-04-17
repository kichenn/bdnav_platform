package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.entity.FenceAlarm;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
public interface FenceAlarmService extends IService<FenceAlarm> {

	/**
	 *查询所有数量
	 */
 	Integer getFenceAlarmAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelFenceAlarmInIds(List<Long> id);

}
