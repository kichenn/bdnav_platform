package com.bdxh.appburied.feign;

import com.bdxh.appburied.dto.AddAppStatusDto;
import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyAppStatusDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.fallback.AppStatusControllerClientFallback;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@FeignClient(value = "appburied-provider-cluster",fallback = AppStatusControllerClientFallback.class)
public interface AppStatusControllerClient {


    @RequestMapping(value = "/appStatus/addAppStatus", method = RequestMethod.POST)
    Wrapper addAppStatus(@Validated @RequestBody AddAppStatusDto addAppStatusDto);

    @RequestMapping(value = "/appStatus/modifyAppStatus", method = RequestMethod.POST)
    Wrapper modifyAppStatus(@Validated @RequestBody ModifyAppStatusDto modifyAppStatusDto);


    @RequestMapping(value = "/appStatus/delAppStatusById", method = RequestMethod.POST)
    Wrapper delAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto appStatusDto);

    @RequestMapping(value = "/appStatus/findAppStatusById", method = RequestMethod.POST)
    Wrapper<AppStatus> findAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto findAppStatusDto);

    @RequestMapping(value = "/appStatus/findAppStatusInContionPaging", method = RequestMethod.POST)
    Wrapper<PageInfo<AppStatus>> findAppStatusInContionPaging(@Validated @RequestBody AppStatusQueryDto appStatusQueryDto);

    @RequestMapping(value = "/appStatus/findAppStatusInfoBySchoolCodeAndCardNumber",method = RequestMethod.POST)
    Wrapper<List<AppStatus>> findAppStatusInfoBySchoolCodeAndCardNumber(@RequestParam("schoolCode")String schoolCode,
                                                                        @RequestParam("cardNumber")String cardNumber);

    @RequestMapping(value = "/appStatus/appStatusLockingAndUnlock",method = RequestMethod.POST)
    Wrapper appStatusLockingAndUnlock(@RequestBody AppStatus appStatus);
}
