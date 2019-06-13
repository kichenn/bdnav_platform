package com.bdxh.system.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.dto.FeedbackQueryDto;
import com.bdxh.system.dto.ModifyFeedbackDto;
import com.bdxh.system.service.FeedbackService;
import com.bdxh.system.vo.FeedbackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @Description: 用户反馈的控制器
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@RestController
@RequestMapping("/feedback")
@Slf4j
@Validated
@Api(value = "用户反馈信息管理", tags = "用户反馈信息的API")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;


	/**
	 * 添加用户反馈信息
	 * @param addFeedbackDto
	 * @return
	 */
	@RequestMapping(value = "/addFeedback",method = RequestMethod.POST)
	@ApiOperation(value = "添加用户反馈信息",response = Boolean.class)
	public Object addFeedback(@Validated @RequestBody AddFeedbackDto addFeedbackDto){
		return WrapMapper.ok(feedbackService.addFeedback(addFeedbackDto));
	}

	/**
	 * 删除用户反馈信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delFeedback",method = RequestMethod.GET)
	@ApiOperation(value = "删除用户反馈信息",response = Boolean.class)
	public Object delFeedback(@RequestParam("id") Long id){
		return WrapMapper.ok(feedbackService.delFeedback(id));
	}

	/**
	 * 修改用户反馈信息
	 * @param modifyFeedbackDto
	 * @return
	 */
	@RequestMapping(value = "/modifyFeedback",method = RequestMethod.POST)
	@ApiOperation(value = "修改用户反馈信息",response = Boolean.class)
	public Object modifyFeedback(@Validated @RequestBody ModifyFeedbackDto modifyFeedbackDto){
		return WrapMapper.ok(feedbackService.modifyFeedback(modifyFeedbackDto));
	}

	/**
	 *  根据条件分页查询用户的反馈信息
	 * @param feedbackQueryDto
	 * @return
	 */
	@RequestMapping(value = "/findFeedbackByCondition",method = RequestMethod.POST)
	@ApiOperation(value = "根据条件分页查询用户的反馈信息",response = FeedbackVo.class)
	public Object findFeedbackByCondition(@Validated @RequestBody FeedbackQueryDto feedbackQueryDto){
		return WrapMapper.ok(feedbackService.findFeedbackByCondition(feedbackQueryDto));
	}



}