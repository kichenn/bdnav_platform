package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.feign.TrajectoryControllerClient;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-24 09:51
 **/
@Component
public class TrajectoryControllerFallback implements TrajectoryControllerClient {

    @Override
    public Wrapper findTrajectoryInfo(String startTime, String endTime, String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper findLatestPoint(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }
}