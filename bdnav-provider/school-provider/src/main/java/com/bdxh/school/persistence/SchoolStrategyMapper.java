package com.bdxh.school.persistence;


import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


/**
* @Description: 持久化
* @Date 2019-04-18 09:52:43
*/
@Repository
public interface SchoolStrategyMapper extends Mapper<SchoolStrategy> {
	//查询所有数量
	public Integer getSchoolStrategyAllCount();
	//批量删除方法
	public Integer delSchoolStrategyInIds(@Param("ids") List<Long> ids);
	//带条件查询
	List<QuerySchoolStrategy> getByCondition(Map<String,Object> param);

	//对比同一学校下策略优先级
	SchoolStrategy getByPriority(@Param("schoolCode") String schoolCode,@Param("priority")Byte priority);

	//根据schoolcode查询学校策略
	List<QuerySchoolStrategy> getStrategyList(@Param("schoolCode") String schoolCode,@Param("pushState") Byte pushState);

	//添加策略
	Integer addSchoolStrategy(SchoolStrategy schoolStrategy);

	//根据id查询策略
    QuerySchoolStrategy findStrategyById(@Param("id") Long id);
}
