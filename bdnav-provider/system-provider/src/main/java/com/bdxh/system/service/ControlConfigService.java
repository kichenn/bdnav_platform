package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.entity.Dept;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-05-30 09:36:37
*/
@Service
public interface ControlConfigService extends IService<ControlConfig> {

	/**
	 *查询所有数量
	 */
 	Integer getControlConfigAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelControlConfigInIds(List<Long> id);

	/**
	 * 查询隐藏/黑名单应用
	 * @param appType
	 * @return
	 */
	List<ControlConfig> findAppType(Byte appType);

	//根据条件查询列表
	PageInfo<ControlConfig> findListPage(Map<String,Object> param, Integer pageNum, Integer pageSize);


}
