package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.ObjectUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddAppConfigSecretDto;
import com.bdxh.system.dto.AppConfigSecretQueryDto;
import com.bdxh.system.dto.UpdateAppConfigSecretDto;
import com.bdxh.system.entity.AppConfigSecret;
import com.bdxh.system.service.AppConfigSecretService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @description: 控制器
 * @author: xuyuan
 * @create: 2019-03-21 17:14
 **/
@RestController
@RequestMapping("/appConfigSecret")
@Validated
@Slf4j
@Api(value = "应用秘钥管理", tags = "应用秘钥管理")
public class AppConfigSecretController {

    @Autowired
    private AppConfigSecretService appConfigSecretService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @ApiOperation("增加应用秘钥")
    @RequestMapping(value = "/addAppConfigSecret",method = RequestMethod.POST)
    public Object addAppConfigSecret(@Valid @RequestBody AddAppConfigSecretDto addAppConfigSecretDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("appId",addAppConfigSecretDto.getAppId());
            param.put("mchName",addAppConfigSecretDto.getMchName());
            param.put("schoolCode",addAppConfigSecretDto.getSchoolCode());
            Integer isAppConfigSecretExist = appConfigSecretService.isAppConfigSecretExist(param);
            Preconditions.checkArgument(isAppConfigSecretExist==null,"应用秘钥已存在");
            AppConfigSecret appConfigSecret = BeanMapUtils.map(addAppConfigSecretDto, AppConfigSecret.class);
            long mchId = snowflakeIdWorker.nextId();
            String appSecret = ObjectUtil.getUuid();
            appConfigSecret.setMchId(mchId);
            appConfigSecret.setAppSecret(appSecret);
            appConfigSecretService.save(appConfigSecret);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id删除应用秘钥")
    @RequestMapping(value = "/delAppConfigSecret",method = RequestMethod.GET)
    public Object delAppConfigSecret(@RequestParam(name = "id") @NotNull(message = "应用秘钥id不能为空") Long id){
        try {
            appConfigSecretService.deleteByKey(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("根据id更新应用")
    @RequestMapping(value = "/updateAppConfigSecret",method = RequestMethod.POST)
    public Object updateAppConfigSecret(@Valid @RequestBody UpdateAppConfigSecretDto updateAppConfigSecretDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            AppConfigSecret appConfigSecret = BeanMapUtils.map(updateAppConfigSecretDto, AppConfigSecret.class);
            appConfigSecretService.update(appConfigSecret);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用秘钥")
    @RequestMapping(value = "/queryAppConfigSecret",method = RequestMethod.GET)
    public Object queryAppConfigSecret(@RequestParam(name = "id") @NotNull(message = "应用秘钥id不能为空") Long id){
        try {
            AppConfigSecret appConfigSecret = appConfigSecretService.selectByKey(id);
            return WrapMapper.ok(appConfigSecret);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询应用秘钥列表")
    @RequestMapping(value = "/queryAppConfigSecretList",method = RequestMethod.POST)
    public Object queryAppConfigSecretList(@Valid @RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appConfigSecretQueryDto);
            List<AppConfigSecret> appConfigSecrets = appConfigSecretService.getAppConfigSecretList(param);
            return WrapMapper.ok(appConfigSecrets);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询应用列表")
    @RequestMapping(value = "/queryAppConfigSecretListPage",method = RequestMethod.POST)
    public Object queryAppConfigSecretListPage(@Valid @RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(appConfigSecretQueryDto);
            PageInfo<AppConfigSecret> appConfigSecrets = appConfigSecretService.getAppConfigSecretListPage(param, appConfigSecretQueryDto.getPageNum(), appConfigSecretQueryDto.getPageSize());
            return WrapMapper.ok(appConfigSecrets);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
