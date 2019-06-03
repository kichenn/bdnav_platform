package com.bdxh.school.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolOrg;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-31 14:06:08
*/
@Repository
public interface SchoolOrgMapper extends Mapper<SchoolOrg> {

	/**
	 *查询总条数
	 */
	 Integer getSchoolOrgAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delSchoolOrgInIds(@Param("ids") List<Long> ids);
}
