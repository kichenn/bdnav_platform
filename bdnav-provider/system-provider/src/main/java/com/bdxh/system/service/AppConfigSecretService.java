package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.AppConfigSecret;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-03-21 16:43:33
*/
public interface AppConfigSecretService extends IService<AppConfigSecret> {

	/**
	 *查询所有数量
	 */
 	Integer getAppConfigSecretAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelAppConfigSecretInIds(List<Long> ids);

	/**
	 * 应用配置秘钥是否存在
	 * @param param
	 * @return
	 */
	Integer isAppConfigSecretExist(Map<String,Object> param);

	/**
	 * 根据条件查询应用秘钥
	 * @param param
	 * @return
	 */
	List<AppConfigSecret> getAppConfigSecretList(Map<String,Object> param);

	/**
	 * 根据条件分页查询应用秘钥
	 * @param param
	 * @return
	 */
	PageInfo<AppConfigSecret> getAppConfigSecretListPage(Map<String,Object> param, int pageNum, int pageSize);

}
