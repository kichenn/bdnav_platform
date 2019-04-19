package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolStrategy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;



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
}
