package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
* @Description: 服务许可的业务层接口
* @Author wanMing
* @Date 2019-05-28 10:24:19
*/
@Service
public interface SchoolServicePermitService extends IService<SchoolServicePermit> {

	/**
	 *	查询数据总记录数
	 * @Author: WanMing
	 * @Date: 2019/5/28 10:59
	 */

 	Integer getSchoolServicePermitAllCount();

	/**
	 * 	根据id批量删除服务许可记录
	 * @Author: WanMing
	 * @Date: 2019/5/28 11:00
	 */
 	Boolean batchDelSchoolServicePermitInIds(List<Long> ids);


 	/**
 	 * 分页+条件查询 学校的服务许可权限
 	 * @Author: WanMing
 	 * @Date: 2019/5/28 16:17
 	 */
	PageInfo<SchoolServicePermitShowVo> findSchoolServicePermitInConditionPage(SchoolServicePermitQueryDto schoolServicePermitQueryDto);


	/**
	 * 根据id查询学校的服务许可权限
	 * @Author: WanMing
	 * @Date: 2019/5/28 16:17
	 */
	SchoolServicePermit findSchoolServicePermitById(Long id);

	/**
	 * 根据学校编码查询学校的服务许可权限记录数
	 * @Author: WanMing
	 * @Date: 2019/5/30 10:17
	 */
	SchoolServicePermit querySchoolServicePermitCountBySchoolCode(String schoolCode);
}
