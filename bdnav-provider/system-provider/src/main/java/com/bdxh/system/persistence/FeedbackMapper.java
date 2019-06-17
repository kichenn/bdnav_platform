package com.bdxh.system.persistence;

import com.bdxh.system.entity.Feedback;
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
public interface FeedbackMapper extends Mapper<Feedback> {

	/**
	 *查询总条数
	 */
	 Integer getFeedbackAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delFeedbackInIds(@Param("ids") List<Long> ids);

	/**
	 * 分页条件查询
	 * @param feedback
	 * @return
	 */
    List<Feedback> findFeedbackByCondition(@Param("feedback") Feedback feedback);
}
