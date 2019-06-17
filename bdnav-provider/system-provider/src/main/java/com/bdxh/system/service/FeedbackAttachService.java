package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.FeedbackAttach;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Service
public interface FeedbackAttachService extends IService<FeedbackAttach> {

	/**
	 *查询所有数量
	 */
 	Integer getFeedbackAttachAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelFeedbackAttachInIds(List<Long> id);

}
