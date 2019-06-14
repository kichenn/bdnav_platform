package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.dto.FeedbackQueryDto;
import com.bdxh.system.dto.ModifyFeedbackDto;
import com.bdxh.system.feign.FeedbackControllerClient;
import com.bdxh.system.vo.FeedbackVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/** 用户反馈的降级服务
 * @author WanMing
 * @date 2019/6/14 10:03
 */
@Component
public class FeedbackControllerClientFallback implements FeedbackControllerClient {


    @Override
    public Wrapper addFeedback(AddFeedbackDto addFeedbackDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delFeedback(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyFeedback(ModifyFeedbackDto modifyFeedbackDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<FeedbackVo>> findFeedbackByCondition(FeedbackQueryDto feedbackQueryDto) {
        return WrapMapper.error();
    }
}
