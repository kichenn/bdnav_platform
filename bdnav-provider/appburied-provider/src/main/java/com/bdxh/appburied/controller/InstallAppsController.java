package com.bdxh.appburied.controller;

import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.service.InstallAppsService;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.ConvertMultipartUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


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
           //设置id
           installApps.setId(snowflakeIdWorker.nextId());
           //设置状态值
           installApps.setPlatform(addInstallAppsDto.getInstallAppsPlatformEnum().getKey());
           MultipartFile multipartFile = ConvertMultipartUtil.base64ToMultipart(addInstallAppsDto.getIconUrl());
           Map<String, String> result = null;
           try {
               result = FileOperationUtils.saveBatchFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
           } catch (Exception e) {
               e.printStackTrace();
           }
           installApps.setIconUrl(result.get("url"));
           installApps.setIconName(result.get("name"));
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

    @RequestMapping(value = "/findInstallAppsInConation", method = RequestMethod.POST)
    @ApiOperation(value = "上报App信息查询", response = InstallApps.class)
    public Object findInstallAppsInConation(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        return WrapMapper.ok(installAppsService.findInstallAppsInConation(schoolCode, cardNumber));
    }


    @ApiOperation(value = "批量新增应用上报信息")
    @RequestMapping(value = "/batchSaveInstallAppsInfo", method = RequestMethod.POST)
    public Object batchSaveInstallAppsInfo(@RequestBody List<AddInstallAppsDto> appInstallList) {
        try {
            return WrapMapper.ok(installAppsService.batchSaveInstallAppsInfo(appInstallList));
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/delByAppPackage", method = RequestMethod.GET)
    @ApiOperation(value = "根据包名删除应用", response = Boolean.class)
    public Object delByAppPackage(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("appPackage") String appPackage) {
        return WrapMapper.ok(installAppsService.delByAppPackage(schoolCode, cardNumber, appPackage));
    }


}