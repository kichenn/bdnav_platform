package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.QuerySchoolChargeDeptDto;
import com.bdxh.school.entity.SchoolChargeDept;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 学校收费部门的业务层接口
* @Author WanMing
* @Date 2019-07-10 18:12:31
*/
@Service
public interface SchoolChargeDeptService extends IService<SchoolChargeDept> {

	/**
	 *查询所有数量
	 */
 	Integer getSchoolChargeDeptAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelSchoolChargeDeptInIds(List<Long> id);

 	/**
 	 * 分页+条件查询
 	 * @Author: WanMing
 	 * @Date: 2019/7/10 19:04
 	 */
    PageInfo<SchoolChargeDeptVo> findSchoolChargeDeptByCondition(QuerySchoolChargeDeptDto querySchoolChargeDeptDto);

    /**
     * 根据schoolCode查询学校的收费部门
     * @Author: WanMing
     * @Date: 2019/7/11 09:44
     */
    List<SchoolChargeDeptVo> findSchoolChargeDeptBySchool(String schoolCode);
}
