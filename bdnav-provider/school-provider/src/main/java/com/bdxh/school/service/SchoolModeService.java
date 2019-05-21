package com.bdxh.school.service;


import com.bdxh.common.support.IService;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolMode;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
* @Description: 业务层接口
* @Date 2019-04-18 09:52:43
*/
public interface SchoolModeService extends IService<SchoolMode> {

	//查询列表信息根据条件
	List<SchoolMode> findSchoolModeInCondition(SchoolMode schoolMode);

	//查询所有数量
	Integer getSchoolModeAllCount();

	//新增方法
	Boolean addSchoolMode(SchoolMode schoolMode);

	//修改方法
	Boolean modifySchoolMode(SchoolMode schoolMode);

	//删除方法
	Boolean delSchoolModeById(Long id);

	//批量删除方法
	Boolean batchDelSchoolModeInIds(List<Long> id);

	//根据ID查询对象的方法
	SchoolMode findSchoolModeById(Long id);

	//根据模式名称查询模式
	SchoolMode getSchoolModesByName(String ModelName,Long SchoolId);

	//根据条件分页查询用户列表
	PageInfo<QuerySchoolMode> findListPage(Map<String,Object> param, Integer pageNum, Integer pageSize);

	//根据适用平台查询
	List<SchoolMode> getListByPlatform(Long schoolId,String platform);
}
