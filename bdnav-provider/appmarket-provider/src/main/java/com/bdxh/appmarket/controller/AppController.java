package com.bdxh.appmarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.appmarket.dto.*;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.service.AppImageService;
import com.bdxh.appmarket.service.AppService;
import com.bdxh.appmarket.service.AppVersionService;
import com.bdxh.appmarket.vo.appDownloadlinkVo;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 应用分类控制器
 * @author: xuyuan
 * @create: 2019-03-05 11:26
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@Validated
@Api(value = "应用管理接口文档", tags = "应用管理接口文档")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private AppImageService appImageService;

    @Autowired
    private AppVersionService appVersionService;

    @ApiOperation("增加应用")
    @RequestMapping(value = "/addApp", method = RequestMethod.POST)
    public Object addApp(@Valid @RequestBody AddAppDto addAppDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Integer isAppExist = appService.isAppExist(addAppDto.getAppPackage());
            Preconditions.checkArgument(isAppExist == null, "应用包名已存在");
            App app = BeanMapUtils.map(addAppDto, App.class);
            AppVersion appVersion = BeanMapUtils.map(addAppDto, AppVersion.class);
            appVersion.setApkUrl(addAppDto.getApkUrl()+"&response-content-disposition=attachment");
            List<AddAppImageDto> addImageDtos = addAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.saveApp(app, appImages, appVersion);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用")
    @RequestMapping(value = "/delApp", method = RequestMethod.POST)
    public Object delApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id) {
        try {
            appService.delApp(id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用")
    @RequestMapping(value = "/updateApp", method = RequestMethod.POST)
    public Object updateApp(@Valid @RequestBody UpdateAppDto updateAppDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            App appData = appService.selectByKey(updateAppDto.getId());
            Preconditions.checkNotNull(appData, "应用不存在");
            if (!StringUtils.equals(updateAppDto.getAppPackage(), appData.getAppPackage())) {
                Integer isAppExist = appService.isAppExist(updateAppDto.getAppPackage());
                Preconditions.checkArgument(isAppExist == null, "应用包名已存在");
            }
            App app = BeanMapUtils.map(updateAppDto, App.class);
            List<AddAppImageDto> addImageDtos = updateAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.updateApp(app, appImages);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用")
    @RequestMapping(value = "/queryApp", method = RequestMethod.GET)
    public Object queryApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id) {
        try {
            App app = appService.selectByKey(id);
            return WrapMapper.ok(app);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用和图片")
    @RequestMapping(value = "/queryAppAndImages", method = RequestMethod.GET)
    public Object queryAppAndImages(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id) {
        try {
            Map<String, Object> param = new HashMap<>();
            App app = appService.selectByKey(id);
            AppVersion appVersion = appVersionService.findNewAppVersion(id);
            param.put("appId", id);
            List<AppImage> appImageList = appImageService.getAppImageList(param);
            param.clear();
            param.put("app", app);
            param.put("apkSize", appVersion.getApkSize());
            param.put("apkName", appVersion.getApkName());
            param.put("images", appImageList);
            String jsonString = JSON.toJSONString(param);
            return WrapMapper.ok(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用列表")
    @RequestMapping(value = "/queryAppList", method = RequestMethod.POST)
    public Object queryAppList(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appQueryDto);
            List<App> apps = appService.getAppList(param);
            return WrapMapper.ok(apps);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用列表")
    @RequestMapping(value = "/queryAppListPage", method = RequestMethod.POST)
    public Object queryAppListPage(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appQueryDto);
            PageInfo<App> appListPage = appService.getAppListPage(param, appQueryDto.getPageNum(), appQueryDto.getPageSize());
            return WrapMapper.ok(appListPage);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("显示全部应用or学校特定应用")
    @RequestMapping(value = "/getApplicationOfCollection", method = RequestMethod.POST)
    public Object getApplicationOfCollection(@Valid @RequestBody QueryAppDto queryAppDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            PageInfo<App> appListPage;
            if (queryAppDto.getSchoolId() == null) {
                appListPage = appService.findAppList(queryAppDto.getPageNum(), queryAppDto.getPageSize());
            } else {
                appListPage = appService.getApplicationOfCollection(queryAppDto.getSchoolId(), queryAppDto.getAppName(), queryAppDto.getPlatform(), queryAppDto.getPageNum(), queryAppDto.getPageSize());
            }
            return WrapMapper.ok(appListPage);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据ids查询应用列表")
    @RequestMapping(value = "/getAppListByids", method = RequestMethod.GET)
    public Object getAppListByids(@RequestParam(name = "ids") String ids) {
        try {
            List<App> app = appService.getAppListByids(ids);
            return WrapMapper.ok(app);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id查询应用")
    @RequestMapping(value = "/versionUpdating", method = RequestMethod.GET)
    public Object versionUpdating(@RequestParam(name = "id") Long id) {
        try {
            App app = appService.versionUpdating(id);
            return WrapMapper.ok(app);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("家长查询学校应用列表")
    @RequestMapping(value = "/familyFindAppInfo", method = RequestMethod.POST)
    public Object familyFindAppInfo(@RequestParam("schoolCode") String schoolCode) {
        try {
            return WrapMapper.ok(appService.familyFindAppInfo(schoolCode));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/pushInstallApps", method = RequestMethod.POST)
    @ApiOperation(value = "推送安装消息给安卓")
    public Object pushInstallApps(@RequestParam("id") Long id,
                                  @RequestParam("userName")String userName,
                                  @RequestParam("cardNumber")String cardNumber,
                                  @RequestParam("clientId")String clientId) {
        //获取应用信息
        App app = appService.selectByKey(id);
        //获取应用最新的版本信息和包
        AppVersion appVersion = appVersionService.findNewAppVersion(id);
        //创建推送安装应用Dto类
        PushAndroidAppInfo pushAndroidAppInfo = new PushAndroidAppInfo();
        pushAndroidAppInfo.setSchoolId(app.getSchoolId());
        pushAndroidAppInfo.setSchoolCode(app.getSchoolCode());
        pushAndroidAppInfo.setIconUrl(app.getIconUrl());
        pushAndroidAppInfo.setIconName(app.getIconName());
        pushAndroidAppInfo.setAppName(app.getAppName());
        pushAndroidAppInfo.setAppDesc(app.getAppDesc());
        pushAndroidAppInfo.setApkName(appVersion.getApkName());
        pushAndroidAppInfo.setApkSize(appVersion.getApkSize());
        pushAndroidAppInfo.setApkUrl(appVersion.getApkUrl());
        pushAndroidAppInfo.setPlatform(app.getPlatform());
        pushAndroidAppInfo.setApkUrlName(appVersion.getApkUrlName());
        pushAndroidAppInfo.setAppVersion(appVersion.getAppVersion());
        pushAndroidAppInfo.setApkUrlName(appVersion.getApkUrlName());
        pushAndroidAppInfo.setUserName(userName);
        pushAndroidAppInfo.setCardNumber(cardNumber);
        //个推请求参数类
        AppPushRequest appPushRequest = new AppPushRequest();
        appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
        appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
        appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
        List<String> clientIds = new ArrayList<>();
        clientIds.add(clientId);
        appPushRequest.setClientId(clientIds);
        //穿透模版:发送后不会在系统通知栏展现，SDK将消息传给第三方应用后需要开发者写展现代码才能看到。
        AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
        appNotificationTemplate.setTitle("安装应用");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",pushAndroidAppInfo);
        System.out.println(jsonObject.toString());
        appNotificationTemplate.setTransmissionContent(jsonObject.toJSONString());
        /*appNotificationTemplate.setUrl();*/
        appNotificationTemplate.setLogo(app.getIconName());
        appNotificationTemplate.setLogoUrl(app.getIconUrl());
        appPushRequest.setAppNotificationTemplate(appNotificationTemplate);
        Map<String, Object> resultMap = GeTuiUtil.appBatchPush(appPushRequest);
        Boolean result = false;
        //如果推送成功就个推会返回 {result=ok, contentId=OSL-0520_2vlMMg1urX5H7l3cxFuwS3}
        if (resultMap.get("result").equals("ok")) {
            result = true;
        }
        return WrapMapper.ok(result);
    }


    @ApiOperation("查询系统预置应用列表")
    @RequestMapping(value = "/thePresetList",method = RequestMethod.GET)
    public Object thePresetList(@RequestParam("preset") Byte preset) {
        try {
            List<appDownloadlinkVo> applink=new ArrayList<>();
            List<App> app = appService.thePresetList(preset);
            for (int i = 0; i < app.size(); i++) {
                appDownloadlinkVo adl=new appDownloadlinkVo();
                adl.setAppName(app.get(i).getAppName());
                adl.setAppPackage(app.get(i).getAppPackage());
                adl.setIconUrl(app.get(i).getIconUrl());
                applink.add(adl);
            }
            return WrapMapper.ok(applink);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询当前学校所有应用接口")
    @RequestMapping(value = "/findTheApplicationList",method = RequestMethod.GET)
    public Object findTheApplicationList(@RequestParam(value="schoolCode",required = false) String schoolCode) {
        try {
            List<appDownloadlinkVo> applink=new ArrayList<>();
            List<ApplicationVersionDto> app = appService.findTheApplicationList(schoolCode);
            for (int i = 0; i < app.size(); i++) {
                appDownloadlinkVo adl=new appDownloadlinkVo();
                adl.setApkUrl(app.get(i).getApkUrl());
                adl.setAppName(app.get(i).getAppName());
                adl.setAppPackage(app.get(i).getAppPackage());
                adl.setIconUrl(app.get(i).getIconUrl());
                applink.add(adl);
            }
            return WrapMapper.ok(applink);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
