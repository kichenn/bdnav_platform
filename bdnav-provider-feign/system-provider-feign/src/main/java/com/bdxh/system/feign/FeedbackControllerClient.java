package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.dto.FeedbackQueryDto;
import com.bdxh.system.dto.ModifyFeedbackDto;
import com.bdxh.system.fallback.FeedbackControllerClientFallback;
import com.bdxh.system.vo.FeedbackVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** 用户反馈的feign
 * @author WanMing
 * @date 2019/6/14 10:01
 */
@Service
@FeignClient(value = "system-provider-cluster",fallback = FeedbackControllerClientFallback.class)
public interface FeedbackControllerClient {


    /**
     * 添加用户反馈信息
     * @param addFeedbackDto
     * @return
     */
    @RequestMapping(value = "/feedback/addFeedback",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addFeedback(@Validated @RequestBody AddFeedbackDto addFeedbackDto);


    /**
     * 删除用户反馈信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/feedback/delFeedback",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delFeedback(@RequestParam("id") Long id);

    /**
     * 修改用户反馈信息
     * @param modifyFeedbackDto
     * @return
     */
    @RequestMapping(value = "/feedback/modifyFeedback",method = RequestMethod.POST)
    @ResponseBody
     Wrapper modifyFeedback(@Validated @RequestBody ModifyFeedbackDto modifyFeedbackDto);

    /**
     *  根据条件分页查询用户的反馈信息
     * @param feedbackQueryDto
     * @return
     */
    @RequestMapping(value = "/feedback/findFeedbackByCondition",method = RequestMethod.POST)
    @ResponseBody
     Wrapper<PageInfo<FeedbackVo>> findFeedbackByCondition(@Validated @RequestBody FeedbackQueryDto feedbackQueryDto);
}
