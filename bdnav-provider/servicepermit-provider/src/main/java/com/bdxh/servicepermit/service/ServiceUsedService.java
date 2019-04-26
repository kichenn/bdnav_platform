package com.bdxh.servicepermit.service;

import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.entity.ServiceUsed;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Service
public interface ServiceUsedService extends IService<ServiceUsed> {

	/**
	 *查询所有数量
	 */
 	Integer getServiceUsedAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelServiceUsedInIds(List<Long> id);

}
