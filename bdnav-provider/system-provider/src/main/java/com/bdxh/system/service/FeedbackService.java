package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.dto.FeedbackQueryDto;
import com.bdxh.system.dto.ModifyFeedbackDto;
import com.bdxh.system.entity.Feedback;
import com.bdxh.system.vo.FeedbackVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 用户的意见反馈业务层接口
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Service
public interface FeedbackService extends IService<Feedback> {

	/**
	 *查询所有数量
	 */
 	Integer getFeedbackAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelFeedbackInIds(List<Long> id);

	/**
	 * 添加用户反馈信息
	 * @param addFeedbackDto
	 * @return
	 */
	Boolean addFeedback(AddFeedbackDto addFeedbackDto);

	/**
	 * 根据反馈信息的主键id删除用户反馈信息
	 * @param id
	 * @return
	 */
	Boolean delFeedback(Long id);

	/**
	 * 修改用户反馈信息
	 * @param modifyFeedbackDto
	 * @return
	 */
	Boolean modifyFeedback(ModifyFeedbackDto modifyFeedbackDto);

	/**
	 * 根据条件分页查询用户反馈信息
	 * @param feedbackQueryDto
	 * @return
	 */
	PageInfo<FeedbackVo> findFeedbackByCondition(FeedbackQueryDto feedbackQueryDto);
}
