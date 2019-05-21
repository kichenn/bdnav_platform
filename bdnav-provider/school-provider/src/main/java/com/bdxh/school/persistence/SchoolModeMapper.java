package com.bdxh.school.persistence;

import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolMode;
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
public interface SchoolModeMapper extends Mapper<SchoolMode> {
	//查询所有数量
	Integer getSchoolModeAllCount();
	//批量删除方法
	Integer delSchoolModeInIds(@Param("ids") List<Long> ids);
	 //带条件查询
     List<QuerySchoolMode> getByCondition(Map<String,Object> param);
     //根据名称查询模式信息
	SchoolMode getSchoolModesByName(@Param("modelName")String modelName,@Param("schoolId")Long schoolId);
	//根据模式平台查询模式
	List<SchoolMode> getListByPlatform(@Param("schoolId") Long schoolId,@Param("platform") String platform);


}
