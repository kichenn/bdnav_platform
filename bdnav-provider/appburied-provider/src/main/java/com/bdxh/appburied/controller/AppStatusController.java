package com.bdxh.appburied.controller;

import com.bdxh.appburied.configration.idgenerator.IdGeneratorProperties;
import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.appburied.service.AppStatusService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    private AppStatusService appStatusService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "/addAppStatus", method = RequestMethod.POST)
    @ApiOperation(value = "增加APP上报状态信息", response = Boolean.class)
    public Object addAppStatus(@Validated @RequestBody AddAppStatusDto addAppStatusDto) {
        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(addAppStatusDto, appStatus);
        //设置状态值
        appStatus.setAppStatus(addAppStatusDto.getAppStatusEnum().getKey());
        appStatus.setPlatform(addAppStatusDto.getInstallAppsPlatformEnum().getKey());
        //设置id
        appStatus.setId(snowflakeIdWorker.nextId());
        return WrapMapper.ok(appStatusService.save(appStatus) > 0);
    }

    @RequestMapping(value = "/modifyAppStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改APP上报状态信息", response = Boolean.class)
    public Object modifyAppStatus(@Validated @RequestBody ModifyAppStatusDto modifyAppStatusDto) {
        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(modifyAppStatusDto, appStatus);
        //设置状态值
        if (modifyAppStatusDto.getAppStatusEnum() != null) {
            appStatus.setAppStatus(modifyAppStatusDto.getAppStatusEnum().getKey());
        }
        if (modifyAppStatusDto.getInstallAppsPlatformEnum() != null) {
            appStatus.setPlatform(modifyAppStatusDto.getInstallAppsPlatformEnum().getKey());
        }
        appStatus.setUpdateDate(new Date());
        return WrapMapper.ok(appStatusService.update(appStatus) > 0);
    }

    @RequestMapping(value = "/delAppStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "删除APP上报状态信息", response = Boolean.class)
    public Object delAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto appStatusDto) {
        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(appStatusDto, appStatus);
        return WrapMapper.ok(appStatusService.delete(appStatus) > 0);
    }

    @RequestMapping(value = "/findAppStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP上报状态信息", response = AppStatus.class)
    public Object findAppStatusById(@Validated @RequestBody DelOrFindAppBuriedDto findAppStatusDto) {
        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(findAppStatusDto, appStatus);
        return WrapMapper.ok(appStatusService.select(appStatus));
    }

    @RequestMapping(value = "/findAppStatusInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App状态信息查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody AppStatusQueryDto appStatusQueryDto) {
        return WrapMapper.ok(appStatusService.findAppStatusInConationPaging(appStatusQueryDto));
    }
}