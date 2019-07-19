package com.bdxh.school.persistence;

import java.util.List;

import com.bdxh.school.dto.QuerySchoolChargeDeptDto;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolChargeDept;



/**
* @Description: 学校收费部门的Mapper
* @Author wanMing
* @Date 2019-07-10 18:12:31
*/
@Repository
public interface SchoolChargeDeptMapper extends Mapper<SchoolChargeDept> {

	/**
	 *查询总条数
	 */
	 Integer getSchoolChargeDeptAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delSchoolChargeDeptInIds(@Param("ids") List<Long> ids);

	 //查询+分页
    List<SchoolChargeDeptVo> findSchoolChargeDeptByCondition(@Param("chargeDept") QuerySchoolChargeDeptDto chargeDept);

    //查询单个学校的收费部门
    List<SchoolChargeDeptVo> findSchoolChargeDeptBySchool(@Param("schoolCode")String schoolCode,@Param("chargeDeptType") Byte chargeDeptType);

    //学校的收费部门和统计收费部门下的消费机数量
	List<SchoolChargeDeptVo> querySchoolChargeDeptAndPosNum(@Param("chargeDept") QuerySchoolChargeDeptDto chargeDept);
}
