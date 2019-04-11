package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.entity.BlackUrl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-11 09:56:14
*/
@Service
public interface BlackUrlService extends IService<BlackUrl> {

	/**
	 *查询所有数量
	 */
 	Integer getBlackUrlAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelBlackUrlInIds(List<Long> id);

}
