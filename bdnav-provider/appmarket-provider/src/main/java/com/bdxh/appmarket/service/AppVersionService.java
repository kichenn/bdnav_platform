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

}
