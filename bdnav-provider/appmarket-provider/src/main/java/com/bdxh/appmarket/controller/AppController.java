package com.bdxh.appmarket.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.appmarket.dto.*;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/addApp",method = RequestMethod.POST)
    public Object addApp(@Valid @RequestBody AddAppDto addAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Integer isAppExist = appService.isAppExist(addAppDto.getAppPackage());
            Preconditions.checkArgument(isAppExist == null,"应用包名已存在");
            App app = BeanMapUtils.map(addAppDto, App.class);
            List<AddAppImageDto> addImageDtos = addAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.saveApp(app,appImages);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用")
    @RequestMapping(value = "/delApp",method = RequestMethod.POST)
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
    @RequestMapping(value = "/updateApp",method = RequestMethod.POST)
    public Object updateApp(@Valid @RequestBody UpdateAppDto updateAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            App appData = appService.selectByKey(updateAppDto.getId());
            Preconditions.checkNotNull(appData,"应用不存在");
            if (!StringUtils.equals(updateAppDto.getAppPackage(),appData.getAppPackage())){
                Integer isAppExist = appService.isAppExist(updateAppDto.getAppPackage());
                Preconditions.checkArgument(isAppExist == null,"应用包名已存在");
            }
            App app = BeanMapUtils.map(updateAppDto, App.class);
            List<AddAppImageDto> addImageDtos = updateAppDto.getAddImageDtos();
            List<AppImage> appImages = BeanMapUtils.mapList(addImageDtos, AppImage.class);
            appService.updateApp(app,appImages);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用")
    @RequestMapping(value = "/queryApp",method = RequestMethod.GET)
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
    @RequestMapping(value = "/queryAppAndImages",method = RequestMethod.GET)
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
    @RequestMapping(value = "/queryAppList",method = RequestMethod.POST)
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
    @RequestMapping(value = "/queryAppListPage",method = RequestMethod.POST)
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


    @ApiOperation("显示全部应用or学校特定应用")
    @RequestMapping(value = "/getApplicationOfCollection",method = RequestMethod.POST)
    public Object getApplicationOfCollection(@Valid @RequestBody AppQueryDto appQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            PageInfo<App> appListPage;
            if(appQueryDto.getSchoolId()==null){
             appListPage  = appService.findAppList(appQueryDto.getPageNum(), appQueryDto.getPageSize());
            }else{
             appListPage = appService.getApplicationOfCollection(appQueryDto.getSchoolId(),appQueryDto.getPageNum(), appQueryDto.getPageSize());
            }
            return WrapMapper.ok(appListPage);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据ids查询应用列表")
    @RequestMapping(value = "/getAppListByids",method = RequestMethod.GET)
    public Object getAppListByids(@RequestParam(name = "ids")String ids){
        try {
          List<App> app=appService.getAppListByids(ids);
            return WrapMapper.ok(app);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation("带条件查询某一学校下的应用列表")
    @RequestMapping(value = "/getAppOfCollection",method = RequestMethod.POST)
    public Object getAppOfCollection(@Valid @RequestBody QueryAppDto queryAppDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(queryAppDto);
            PageInfo<App> appListPage = appService.getAppListPage(param, queryAppDto.getPageNum(), queryAppDto.getPageSize());
            return WrapMapper.ok(appListPage);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
