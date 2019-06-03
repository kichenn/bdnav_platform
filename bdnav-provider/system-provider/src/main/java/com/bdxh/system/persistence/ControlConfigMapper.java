package com.bdxh.system.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.entity.Dept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-30 09:36:37
*/
@Repository
public interface ControlConfigMapper extends Mapper<ControlConfig> {

	/**
	 *查询总条数
	 */
	 Integer getControlConfigAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delControlConfigInIds(@Param("ids") List<Long> ids);

	 List<ControlConfig> findAppType(@Param("appType") Byte appType);

	 //带条件分页查询
	 List<ControlConfig> getByCondition(Map<String,Object> param);

}
