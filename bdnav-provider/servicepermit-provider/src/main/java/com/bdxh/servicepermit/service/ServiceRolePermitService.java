package com.bdxh.servicepermit.service;


import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.entity.ServiceRolePermit;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-05-31 11:36:26
*/
@Service
public interface ServiceRolePermitService extends IService<ServiceRolePermit> {

	/**
	 *查询所有数量
	 */
 	Integer getServiceRolePermitAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelServiceRolePermitInIds(List<Long> id);

}
