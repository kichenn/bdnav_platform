package com.bdxh.system.persistence;

import com.bdxh.system.entity.FeedbackAttach;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;



/**
* @Description: Mapper
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Repository
public interface FeedbackAttachMapper extends Mapper<FeedbackAttach> {

	/**
	 *查询总条数
	 */
	 Integer getFeedbackAttachAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delFeedbackAttachInIds(@Param("ids") List<Long> ids);

	/**
	 * 根据反馈信息的id查询对应的附件信息
	 * @param feedId
	 * @return
	 */
	 List<FeedbackAttach> queryFeedbackAttackByFeedId(@Param("feedId")Long feedId);
}
