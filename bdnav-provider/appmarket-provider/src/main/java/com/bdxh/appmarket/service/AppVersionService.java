package com.bdxh.appmarket.service;

import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.common.support.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@Service
public interface AppVersionService extends IService<AppVersion> {

	/**
	 *查询所有数量
	 */
 	Integer getAppVersionAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelAppVersionInIds(List<Long> id);

	/**
	 * 添加app新的版本信息
	 * @param appVersion
	 * @return
	 */
 	void addAppVersionInfo(AppVersion appVersion);

	/**
	 * 查看APP版本历史
	 * @param appId
	 * @return
	 */
 	List<AppVersion> findAppVersion(Long appId);

	/**
	 * 查询最新的应用版本
	 * @param appId
	 * @return
	 */
	AppVersion findNewAppVersion(Long appId);

}
