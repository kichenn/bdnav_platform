package com.bdxh.client.controller.appburied;

import com.bdxh.appburied.dto.AddAppStatusDto;
import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyAppStatusDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/appStatus")
@Slf4j
@Validated
@Api(value = "APP上报状态", tags = "APP上报状态交互API")
public class AppStatusController {

    @Autowired
    private AppStatusControllerClient appStatusControllerClient;


    @RequestMapping(value = "/addAppStatus", method = RequestMethod.POST)
    @ApiOperation(value = "增加APP上报状态信息", response = Boolean.class)
    public Object addAppStatus(@Validated @RequestBody AddAppStatusDto addAppStatusDto) {
        Wrapper wrapper= appStatusControllerClient.addAppStatus(addAppStatusDto);
        return wrapper;
    }

    @RequestMapping(value = "/modifyAppStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改APP上报状态信息", response = Boolean.class)
    public Object modifyAppStatus(@Validated @RequestBody ModifyAppStatusDto modifyAppStatusDto) {
        Wrapper wrapper= appStatusControllerClient.modifyAppStatus(modifyAppStatusDto);
        return wrapper;
    }

    @RequestMapping(value = "/delAppStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "删除APP上报状态信息", response = Boolean.class)
    public Object delAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto appStatusDto) {
        Wrapper wrapper=  appStatusControllerClient.delAppStatusById(appStatusDto);
        return wrapper;
    }

    @RequestMapping(value = "/findAppStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP上报状态信息", response = AppStatus.class)
    public Object findAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto findAppStatusDto) {
       Wrapper wrapper= appStatusControllerClient.findAppStatusById(findAppStatusDto);
        return wrapper;
    }

    @RequestMapping(value = "/findAppStatusInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App状态信息查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody AppStatusQueryDto appStatusQueryDto) {
        return WrapMapper.ok(appStatusControllerClient.findAppStatusInContionPaging(appStatusQueryDto));
    }
}