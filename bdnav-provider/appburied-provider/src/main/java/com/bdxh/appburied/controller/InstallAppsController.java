package com.bdxh.appburied.controller;

import com.bdxh.appburied.configration.idgenerator.IdGeneratorProperties;
import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.service.InstallAppsService;
import com.bdxh.common.utils.SnowflakeIdWorker;
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

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "/addInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "增加上报APP信息", response = Boolean.class)
    public Object addInstallApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(addInstallAppsDto, installApps);
        //设置状态值
        installApps.setPlatform(addInstallAppsDto.getInstallAppsPlatformEnum().getKey());
        //设置id
        installApps.setId(snowflakeIdWorker.nextId());
        return WrapMapper.ok(installAppsService.save(installApps) > 0);
    }

    @RequestMapping(value = "/modifyInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "修改上报APP信息", response = Boolean.class)
    public Object modifyInstallApp(@Validated @RequestBody ModifyInstallAppsDto modifyInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(modifyInstallAppsDto, installApps);
        if (modifyInstallAppsDto.getInstallAppsPlatformEnum() != null) {
            installApps.setPlatform(modifyInstallAppsDto.getInstallAppsPlatformEnum().getKey());
        }
        return WrapMapper.ok(installAppsService.update(installApps) > 0);
    }

    @RequestMapping(value = "/delInstallAppById", method = RequestMethod.POST)
    @ApiOperation(value = "删除上报APP信息", response = Boolean.class)
    public Object delInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto delInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(delInstallAppsDto, installApps);
        return WrapMapper.ok(installAppsService.delete(installApps) > 0);
    }

    @RequestMapping(value = "/findInstallAppById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询上报APP信息", response = InstallApps.class)
    public Object findInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto findInstallAppsDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(findInstallAppsDto, installApps);
        return WrapMapper.ok(installAppsService.select(installApps));
    }

    @RequestMapping(value = "/findInstallAppsInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App信息查询", response = InstallApps.class)
    public Object findInstallAppsInContionPaging(@Validated @RequestBody InstallAppsQueryDto installAppsQueryDto) {
        return WrapMapper.ok(installAppsService.findInstallAppsInConationPaging(installAppsQueryDto));
    }
}