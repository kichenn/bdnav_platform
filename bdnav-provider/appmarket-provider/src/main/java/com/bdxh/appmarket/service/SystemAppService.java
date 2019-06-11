package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.SystemApp;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Date 2019-06-05 15:05:12
*/
@Service
public interface SystemAppService extends IService<SystemApp> {

	/**
	 *查询所有数量
	 */
 	Integer getSystemAppAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelSystemAppInIds(List<Long> id);
	/**
	 * 带条件查询管控应用
	 */
	PageInfo<SystemApp> findAppControl(Map<String,Object> param, Integer pageNum, Integer pageSize);

	/**
	 * 返回最新的一条管控应用信息
	 * @return
	 */
	SystemApp versionUpdating();
}
