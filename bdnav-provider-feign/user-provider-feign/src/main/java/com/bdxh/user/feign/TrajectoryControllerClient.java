package com.bdxh.user.feign;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.fallback.TeacherDeptControllerFallback;
import com.bdxh.user.fallback.TrajectoryControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;


/**
 * @description:
 * @author: binzh
 * @create: 2019-05-24 09:51
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = TrajectoryControllerFallback.class)
public interface TrajectoryControllerClient {

    @RequestMapping(value = "/trajectory/findTrajectoryInfo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper findTrajectoryInfo(@RequestParam("startTime") String startTime,
                               @RequestParam("endTime") String endTime,
                               @RequestParam("accountId") String accountId);

    @RequestMapping(value = "/trajectory/findLatestPoint", method = RequestMethod.GET)
    @ResponseBody
    Wrapper findLatestPoint(@RequestParam("accountId") String accountId);
}

