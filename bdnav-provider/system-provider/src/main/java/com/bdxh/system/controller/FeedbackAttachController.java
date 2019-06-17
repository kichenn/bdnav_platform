package com.bdxh.system.controller;

import com.bdxh.system.service.FeedbackAttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* @Description: 控制器
* @Author wanMing
* @Date 2019-06-13 11:43:51
*/
@Controller
@RequestMapping("/feedbackAttach")
@Slf4j
@Validated
@Api(value = "FeedbackAttach控制器", tags = "FeedbackAttach")
public class FeedbackAttachController {

	@Autowired
	private FeedbackAttachService feedbackAttachService;

}