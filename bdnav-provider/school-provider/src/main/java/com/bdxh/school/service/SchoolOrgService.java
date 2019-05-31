package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.entity.SchoolOrg;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-05-31 14:06:08
*/
@Service
public interface SchoolOrgService extends IService<SchoolOrg> {

	/**
	 *查询所有数量
	 */
 	Integer getSchoolOrgAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelSchoolOrgInIds(List<Long> id);

}
