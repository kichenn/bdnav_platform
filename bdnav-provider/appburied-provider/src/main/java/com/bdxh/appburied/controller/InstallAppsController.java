package com.bdxh.appburied.controller;

import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.service.InstallAppsService;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/installApps")
@Slf4j
@Validated
@Api(value = "上报APP应用信息", tags = "上报APP应用信息交互API")
public class InstallAppsController {

    @Autowired
    private InstallAppsService installAppsService;


    @RequestMapping(value = "/addInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "增加上报APP信息", response = Boolean.class)
    public Object addInstallApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(addInstallAppsDto, installApps);
        return WrapMapper.ok(installAppsService.save(installApps) > 0);
    }

    @RequestMapping(value = "/modifyInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "修改上报APP信息", response = Boolean.class)
    public Object modifyInstallApp(@Validated @RequestBody ModifyInstallAppsDto modifyInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(modifyInstallAppsDto, installApps);
        return WrapMapper.ok(installAppsService.update(installApps) > 0);
    }

    @RequestMapping(value = "/delSchoolById", method = RequestMethod.POST)
    @ApiOperation(value = "删除上报APP信息", response = Boolean.class)
    public Object delSchoolById(@Validated @RequestBody DelInstallAppsDto delInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(delInstallAppsDto, installApps);
        return WrapMapper.ok(installAppsService.delete(installApps) > 0);
    }

    @RequestMapping(value = "/findInstallAppsInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App信息查询", response = Boolean.class)
    public Object addSchool(@Validated @RequestBody InstallAppsQueryDto installAppsQueryDto) {
        return WrapMapper.ok();
    }
}