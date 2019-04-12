package com.bdxh.appburied.service;

import com.bdxh.common.support.IService;
import com.bdxh.appburied.entity.ApplyLog;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Service
public interface ApplyLogService extends IService<ApplyLog> {

	/**
	 *查询所有数量
	 */
 	Integer getApplyLogAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelApplyLogInIds(List<Long> id);

}
