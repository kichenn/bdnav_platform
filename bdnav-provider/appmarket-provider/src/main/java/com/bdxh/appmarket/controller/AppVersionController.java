package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.dto.AddAppVersionDto;
import com.bdxh.appmarket.dto.AppVersionQueryDto;
import com.bdxh.appmarket.dto.UpdateAppVersionDto;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.service.AppVersionService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 应用版本管理控制器
 * @author: xuyuan
 * @create: 2019-03-05 17:48
 **/
@RestController
@RequestMapping("/appVersion")
@Slf4j
@Validated
@Api(value = "应用版本管理接口文档", tags = "应用版本管理接口文档")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @ApiOperation("增加应用版本")
    @RequestMapping("/addAppVersion")
    public Object addCategory(@Valid @RequestBody AddAppVersionDto addAppVersionDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Integer isAppVersionExist = appVersionService.isAppVersionExist(addAppVersionDto.getAppId(), addAppVersionDto.getAppCode());
            Preconditions.checkArgument(isAppVersionExist == null, "应用版本已存在");
            AppVersion appVersion = BeanMapUtils.map(addAppVersionDto, AppVersion.class);
            appVersionService.save(appVersion);
            return WrapMapper.ok(appVersion);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用版本")
    @RequestMapping("/delAppVersion")
    public Object delApp(@RequestParam(name = "id") @NotNull(message = "应用版本id不能为空") Long id){
        try {
            appVersionService.deleteByKey(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用版本")
    @RequestMapping("/updateAppVersion")
    public Object updateAppVersion(@Valid @RequestBody UpdateAppVersionDto updateAppVersionDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppVersion appVersion = BeanMapUtils.map(updateAppVersionDto, AppVersion.class);
            appVersionService.update(appVersion);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用版本")
    @RequestMapping("/queryAppVersion")
    public Object queryAppVersion(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            AppVersion appVersion = appVersionService.selectByKey(id);
            return WrapMapper.ok(appVersion);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用版本列表")
    @RequestMapping("/queryAppVersionList")
    public Object queryAppVersionList(@Valid @RequestBody AppVersionQueryDto appVersionQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appVersionQueryDto);
            List<AppVersion> appVersions = appVersionService.queryAppVersionList(param);
            return WrapMapper.ok(appVersions);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用版本列表")
    @RequestMapping("/queryAppVersionListPage")
    public Object queryAppVersionListPage(@Valid @RequestBody AppVersionQueryDto appVersionQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appVersionQueryDto);
            PageInfo<AppVersion> appVersions = appVersionService.queryAppVersionListPage(param,appVersionQueryDto.getPageNum(),appVersionQueryDto.getPageSize());
            return WrapMapper.ok(appVersions);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
