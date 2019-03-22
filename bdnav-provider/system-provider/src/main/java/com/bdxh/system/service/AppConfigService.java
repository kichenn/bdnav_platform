package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.AppConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-03-21 16:43:33
*/
public interface AppConfigService extends IService<AppConfig> {

	/**
	 *查询所有数量
	 */
 	Integer getAppConfigAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelAppConfigInIds(List<Long> ids);

	/**
	 * 根据appName查询应用配置
	 * @param appName
	 * @return
	 */
	AppConfig getByAppConfigName(String appName);

	/**
	 * 根据条件查询应用配置
	 * @param param
	 * @return
	 */
	List<AppConfig> getAppConfigList(Map<String,Object> param);

	/**
	 * 根据条件分页查询应用配置
	 * @param param
	 * @return
	 */
	PageInfo<AppConfig> getAppConfigListPage(Map<String,Object> param, int pageNum, int pageSize);

}
