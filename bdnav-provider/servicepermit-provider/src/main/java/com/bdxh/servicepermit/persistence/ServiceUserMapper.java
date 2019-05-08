package com.bdxh.servicepermit.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.servicepermit.entity.ServiceUser;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Repository
public interface ServiceUserMapper extends Mapper<ServiceUser> {

	/**
	 *查询总条数
	 */
	 Integer getServiceUserAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delServiceUserInIds(@Param("ids") List<Long> ids);

	/**
	 * 根据条件查询服务列表必传
	 * @param param
	 * @return
	 */
	List<ServiceUser> getServiceByCondition(Map<String,Object> param);


	Integer deleteByServiceId(@Param("SchoolCode")String SchoolCode,@Param("cardNumber") Long cardNumber,@Param("id")Long id);



}
