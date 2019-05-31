package com.bdxh.school.persistence;

import java.util.List;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: 学校服务许可Mapper接口
* @Author WanMing
* @Date 2019-05-28 10:24:19
*/
@Repository
public interface SchoolServicePermitMapper extends Mapper<SchoolServicePermit> {


	/**
	 * 查询服务许可的总条数
	 * @Author: wanMing
	 * @Date: 2019/5/28 10:38
	 */
	 Integer getSchoolServicePermitAllCount();

	 /**

	  * 批量删除服务许可的记录数
	  * @Author: wanMing
	  * @Date: 2019/5/28 10:38
	  */
	 Integer delSchoolServicePermitInIds(@Param("ids") List<Long> ids);

	 /**
	  *	分页条件查询学校服务许可信息
	  * @Author: WanMing
	  * @Date: 2019/5/28 16:35
	  */
	 List<SchoolServicePermit> findSchoolServicePermitInConditionPage(@Param("schoolServicePermit") SchoolServicePermit schoolServicePermit);


	 /**
	  * 根据学校编码查询服务许可记录
	  * @Author: WanMing
	  * @Date: 2019/5/30 10:04
	  */
     SchoolServicePermit querySchoolServicePermitCountBySchoolCode(@Param("schoolCode") String schoolCode);
}
