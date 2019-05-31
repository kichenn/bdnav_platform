package com.bdxh.servicepermit.persistence;

import java.util.List;

import com.bdxh.servicepermit.entity.ServiceRole;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-31 11:36:26
*/
@Repository
public interface ServiceRoleMapper extends Mapper<ServiceRole> {

	/**
	 *查询总条数
	 */
	 Integer getServiceRoleAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delServiceRoleInIds(@Param("ids") List<Long> ids);
}
