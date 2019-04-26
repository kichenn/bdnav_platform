package com.bdxh.servicepermit.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.servicepermit.entity.ServiceUsed;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Repository
public interface ServiceUsedMapper extends Mapper<ServiceUsed> {

	/**
	 *查询总条数
	 */
	 Integer getServiceUsedAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delServiceUsedInIds(@Param("ids") List<Long> ids);
}
