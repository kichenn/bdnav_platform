package com.bdxh.appmarket.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.appmarket.dto.AddAppDto;
import com.bdxh.appmarket.dto.AddImageDto;
import com.bdxh.appmarket.dto.AppQueryDto;
import com.bdxh.appmarket.dto.UpdateAppDto;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.entity.AppImage;
import com.bdxh.appmarket.service.AppImageService;
import com.bdxh.appmarket.service.AppService;
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

    @ApiOperation("增加应用")
    @RequestMapping("/addApp")
    public Object addCategory(@Valid @RequestBody AddAppDto addAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Integer isAppExist = appService.isAppExist(addAppDto.getAppPackage());
            Preconditions.checkArgument(isAppExist == null,"应用包名已存在");
            App app = BeanMapUtils.map(addAppDto, App.class);
            List<AddImageDto> addImageDtos = addAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.saveApp(app,appImages);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用")
    @RequestMapping("/delApp")
    public Object delApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            appService.delApp(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用")
    @RequestMapping("/updateApp")
    public Object updateApp(@Valid @RequestBody UpdateAppDto updateAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            App app = BeanMapUtils.map(updateAppDto, App.class);
            List<AddImageDto> addImageDtos = updateAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.updateApp(app,appImages);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用")
    @RequestMapping("/queryApp")
    public Object queryApp(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            App app = appService.selectByKey(id);
            return WrapMapper.ok(app);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用和图片")
    @RequestMapping("/queryAppAndImages")
    public Object queryAppAndImages(@RequestParam(name = "id") @NotNull(message = "应用id不能为空") Long id){
        try {
            Map<String,Object> param = new HashMap<>();
            App app = appService.selectByKey(id);
            param.put("appId",id);
            List<AppImage> appImageList = appImageService.getAppImageList(param);
            param.clear();
            param.put("app",app);
            param.put("images",appImageList);
            String jsonString = JSON.toJSONString(param);
            return WrapMapper.ok(jsonString);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用列表")
    @RequestMapping("/queryAppList")
    public Object queryAppList(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appQueryDto);
            List<App> apps = appService.getAppList(param);
            return WrapMapper.ok(apps);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用列表")
    @RequestMapping("/queryAppListPage")
    public Object queryAppListPage(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appQueryDto);
            PageInfo<App> appListPage = appService.getAppListPage(param, appQueryDto.getPageNum(), appQueryDto.getPageSize());
            return WrapMapper.ok(appListPage);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
