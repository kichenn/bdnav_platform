package com.bdxh.backend.controller.appburied;

import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.appburied.dto.ModifyInstallAppsDto;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
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
@RequestMapping("/installApps")
@Slf4j
@Validated
@Api(value = "上报APP应用信息", tags = "上报APP应用信息交互API")
public class InstallAppsController {

    @Autowired
    private InstallAppsControllerClient installAppsControllerClient;



    @RequestMapping(value = "/addInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "增加上报APP信息", response = Boolean.class)
    public Object addInstallApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto) {
        Wrapper wrapper= installAppsControllerClient.addInstallApp(addInstallAppsDto);
        return wrapper;
    }

    @RequestMapping(value = "/modifyInstallApp", method = RequestMethod.POST)
    @ApiOperation(value = "修改上报APP信息", response = Boolean.class)
    public Object modifyInstallApp(@Validated @RequestBody ModifyInstallAppsDto modifyInstallAppsDto) {
        Wrapper wrapper= installAppsControllerClient.modifyInstallApp(modifyInstallAppsDto);
        return wrapper;
    }

    @RequestMapping(value = "/delInstallAppById", method = RequestMethod.POST)
    @ApiOperation(value = "删除上报APP信息", response = Boolean.class)
    public Object delInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto delInstallAppsDto) {
        Wrapper wrapper= installAppsControllerClient.delInstallAppById(delInstallAppsDto);
        return wrapper;
    }

    @RequestMapping(value = "/findInstallAppById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询上报APP信息", response = InstallApps.class)
    public Object findInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto findInstallAppsDto) {
        Wrapper wrapper= installAppsControllerClient.findInstallAppById(findInstallAppsDto);
        return wrapper;
    }

    @RequestMapping(value = "/findInstallAppsInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App信息查询", response = InstallApps.class)
    public Object findInstallAppsInContionPaging(@Validated @RequestBody InstallAppsQueryDto installAppsQueryDto) {
        return WrapMapper.ok(installAppsControllerClient.findInstallAppsInContionPaging(installAppsQueryDto));
    }
}