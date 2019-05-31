package com.bdxh.servicepermit.persistence;

import java.util.List;

import com.bdxh.servicepermit.entity.ServiceRolePermit;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-31 11:36:26
*/
@Repository
public interface ServiceRolePermitMapper extends Mapper<ServiceRolePermit> {

	/**
	 *查询总条数
	 */
	 Integer getServiceRolePermitAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delServiceRolePermitInIds(@Param("ids") List<Long> ids);
}
