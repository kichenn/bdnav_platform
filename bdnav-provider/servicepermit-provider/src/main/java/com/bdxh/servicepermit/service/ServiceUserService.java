package com.bdxh.servicepermit.service;

import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Service
public interface ServiceUserService extends IService<ServiceUser> {

	/**
	 *查询所有数量
	 */
 	Integer getServiceUsedAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelServiceUsedInIds(List<Long> id);
	/**
	 * 根据条件查询用户订单列表
	 * @param param
	 * @return
	 */
	PageInfo<ServiceUser> getServiceByCondition(Map<String,Object> param, Integer pageNum, Integer pageSize);

	/**
	 * 删除用户许可
	 * @param SchoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	Boolean deleteByServiceId(String SchoolCode,Long cardNumber,Long id);


}
