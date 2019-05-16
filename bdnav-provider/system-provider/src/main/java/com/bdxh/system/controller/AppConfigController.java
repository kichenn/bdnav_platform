package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddAppConfigDto;
import com.bdxh.system.dto.AppConfigQueryDto;
import com.bdxh.system.dto.UpdateAppConfigDto;
import com.bdxh.system.entity.AppConfig;
import com.bdxh.system.service.AppConfigSecretService;
import com.bdxh.system.service.AppConfigService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.ValueExp;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 控制器
 * @author: xuyuan
 * @create: 2019-03-21 17:09
 **/
@RestController
@RequestMapping("/appConfig")
@Validated
@Slf4j
@Api(value = "应用配置管理", tags = "应用配置管理")
public class AppConfigController {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AppConfigSecretService appConfigSecretService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;



    @ApiOperation("增加应用配置")
    @RequestMapping(value = "/addAppConfig",method = RequestMethod.POST)
    public Object addAppConfig(@Valid @RequestBody AddAppConfigDto addAppConfigDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppConfig appConfigData = appConfigService.getByAppConfigName(addAppConfigDto.getAppName());
            Preconditions.checkArgument(appConfigData == null, "该应用已存在");
       /*     Preconditions.checkNotNull(appConfigData,"应用已存在");*/
            AppConfig appConfig = BeanMapUtils.map(addAppConfigDto, AppConfig.class);
            long appId = snowflakeIdWorker.nextId();
            appConfig.setAppId(appId);
            appConfigService.save(appConfig);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用配置")
    @RequestMapping(value = "/delAppConfig",method = RequestMethod.GET)
    public Object delAppConfig(@RequestParam(name = "id") @NotNull(message = "应用配置id不能为空") Long id){
        try {
            AppConfig appConfig = appConfigService.selectByKey(id);
            Preconditions.checkArgument(appConfig!=null,"应用不存在");
            Map<String,Object> param = new HashMap<>();
            param.put("appId",appConfig.getAppId());
            Integer isAppConfigSecretExist = appConfigSecretService.isAppConfigSecretExist(param);
            Preconditions.checkArgument(isAppConfigSecretExist==null,"该应用下已生成秘钥");
            appConfigService.deleteByKey(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用配置")
    @RequestMapping(value = "/updateAppConfig",method = RequestMethod.POST)
    public Object updateAppConfig(@Valid @RequestBody UpdateAppConfigDto updateAppConfigDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppConfig appConfig = BeanMapUtils.map(updateAppConfigDto, AppConfig.class);
            appConfigService.update(appConfig);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用配置")
    @RequestMapping(value = "/queryAppConfig",method = RequestMethod.GET)
    public Object queryAppConfig(@RequestParam(name = "id") @NotNull(message = "应用配置id不能为空") Long id){
        try {
            AppConfig appConfig = appConfigService.selectByKey(id);
            return WrapMapper.ok(appConfig);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用配置列表")
    @RequestMapping(value = "/queryAppConfigList",method = RequestMethod.POST)
    public Object queryAppConfigList(@Valid @RequestBody AppConfigQueryDto appConfigQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appConfigQueryDto);
            List<AppConfig> pageInfo = appConfigService.getAppConfigList(param);
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用配置列表")
    @RequestMapping(value = "/queryAppConfigListPage",method = RequestMethod.POST)
    public Object queryAppConfigListPage(@Valid @RequestBody AppConfigQueryDto appConfigQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appConfigQueryDto);
            PageInfo<AppConfig> pageInfo = appConfigService.getAppConfigListPage(param, appConfigQueryDto.getPageNum(), appConfigQueryDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
