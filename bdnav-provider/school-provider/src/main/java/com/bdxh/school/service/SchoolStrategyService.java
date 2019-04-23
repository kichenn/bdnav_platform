package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
* @Description: 业务层接口
* @Date 2019-04-18 09:52:43
*/

public interface SchoolStrategyService extends IService<SchoolStrategy> {

	//查询列表信息根据条件
	List<SchoolStrategy> findSchoolStrategyInCondition(SchoolStrategy schoolStrategy);

	//查询所有数量
	Integer getSchoolStrategyAllCount();

	//新增方法
	Boolean addSchoolStrategy(SchoolStrategy schoolStrategy);

	//修改方法
	Boolean modifySchoolStrategy(SchoolStrategy schoolStrategy);

	//删除方法
	Boolean delSchoolStrategyById(Long id);

	//批量删除方法
	Boolean batchDelSchoolStrategyInIds(List<Long> id);

	//根据ID查询对象的方法
	SchoolStrategy findSchoolStrategyById(Long id);

	//根据条件分页查询用户列表
	PageInfo<QuerySchoolStrategy> findListPage(Map<String,Object> param, Integer pageNum, Integer pageSize);

}