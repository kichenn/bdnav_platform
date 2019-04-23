package com.bdxh.appburied.feign;

import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.fallback.ApplyLogControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@FeignClient(value = "appburied-provider-cluster",fallback = ApplyLogControllerClientFallback.class)
public interface ApplyLogControllerClient {

    @RequestMapping(value = "/applyLog/addApplyLog", method = RequestMethod.POST)
    Wrapper addApplyLog(@Validated @RequestBody AddApplyLogDto addApplyLogDto);

    @RequestMapping(value = "/applyLog/modifyApplyLog", method = RequestMethod.POST)
    Wrapper modifyApplyLog(@Validated @RequestBody ModifyApplyLogDto modifyApplyLogDto);

    @RequestMapping(value = "/applyLog/delApplyLogById", method = RequestMethod.POST)
    Wrapper delApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto AddapplyLogDto);

    @RequestMapping(value = "/applyLog/findApplyLogById", method = RequestMethod.POST)
    Wrapper<ApplyLog> findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto);

    @RequestMapping(value = "/applyLog/findApplyLogInContionPaging", method = RequestMethod.POST)
    Wrapper<PageInfo<ApplyLog>> findApplyLogInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto);

}