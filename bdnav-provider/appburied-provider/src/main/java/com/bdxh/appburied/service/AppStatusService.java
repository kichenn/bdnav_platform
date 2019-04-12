package com.bdxh.appburied.service;

import com.bdxh.common.support.IService;
import com.bdxh.appburied.entity.AppStatus;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Service
public interface AppStatusService extends IService<AppStatus> {

	/**
	 *查询所有数量
	 */
 	Integer getAppStatusAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelAppStatusInIds(List<Long> id);

}
