package com.bdxh.backend.controller.system;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.dto.FeedbackQueryDto;
import com.bdxh.system.dto.ModifyFeedbackDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.FeedbackControllerClient;
import com.bdxh.system.vo.FeedbackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** 用户反馈web层
 * @author WanMing
 * @date 2019/6/14 10:20
 */
@RestController
@RequestMapping("/feedbackWebController")
@Validated
@Slf4j
@Api(value = "用户反馈信息管理", tags = "用户反馈信息管理API")
public class FeedbackWebController {


    @Autowired
    private FeedbackControllerClient feedbackControllerClient;


    /**
     * 添加用户反馈信息
     * @param addFeedbackDto
     * @return
     */
    @RequestMapping(value = "/addFeedback",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户反馈信息",response = Boolean.class)
    public Object addFeedback(@Validated @RequestBody AddFeedbackDto addFeedbackDto){
        //添加操作人的信息
        User currentUser = SecurityUtils.getCurrentUser();
        addFeedbackDto.setOperator(currentUser.getId());
        addFeedbackDto.setOperatorName(currentUser.getUserName());
        return feedbackControllerClient.addFeedback(addFeedbackDto);
    }

    /**
     * 删除用户反馈信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delFeedback",method = RequestMethod.GET)
    @ApiOperation(value = "删除用户反馈信息",response = Boolean.class)
    public Object delFeedback(@RequestParam("id") Long id){
        return feedbackControllerClient.delFeedback(id);
    }

    /**
     * 修改用户反馈信息
     * @param modifyFeedbackDto
     * @return
     */
    @RequestMapping(value = "/modifyFeedback",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户反馈信息",response = Boolean.class)
    public Object modifyFeedback(@Validated @RequestBody ModifyFeedbackDto modifyFeedbackDto){
        //添加修改人的信息
        User currentUser = SecurityUtils.getCurrentUser();
        modifyFeedbackDto.setOperator(currentUser.getId());
        modifyFeedbackDto.setOperatorName(currentUser.getUserName());
        return feedbackControllerClient.modifyFeedback(modifyFeedbackDto);
    }

    /**
     *  根据条件分页查询用户的反馈信息
     * @param feedbackQueryDto
     * @return
     */
    @RequestMapping(value = "/findFeedbackByCondition",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页查询用户的反馈信息",response = FeedbackVo.class)
    public Object findFeedbackByCondition(@Validated @RequestBody FeedbackQueryDto feedbackQueryDto){
        return feedbackControllerClient.findFeedbackByCondition(feedbackQueryDto);
    }
}
