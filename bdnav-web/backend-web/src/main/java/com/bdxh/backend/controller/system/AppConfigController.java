package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigDto;
import com.bdxh.system.dto.AppConfigQueryDto;
import com.bdxh.system.dto.UpdateAppConfigDto;
import com.bdxh.system.entity.AppConfig;
import com.bdxh.system.feign.AppConfigControllerClient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 控制器
 * @author: xuyuan
 * @create: 2019-03-21 17:09
 **/
@RestController
@RequestMapping("/appConfigWeb")
@Validated
@Slf4j
@Api(value = "应用配置管理", tags = "应用配置管理")
public class AppConfigController {

    @Autowired
    private AppConfigControllerClient appConfigControllerClient;

    @ApiOperation(value="增加应用配置",response = Boolean.class)
    @RequestMapping(value="/addAppConfig", method = RequestMethod.POST)
    public Object addAppConfig(@Validated @RequestBody AddAppConfigDto addAppConfigDto){
        Wrapper wrapper = appConfigControllerClient.addAppConfig(addAppConfigDto);
        return wrapper;
    }

    @ApiOperation(value="根据id删除应用配置",response = Boolean.class)
    @RequestMapping(value="/delAppConfig",method = RequestMethod.GET)
    public Object delAppConfig(@RequestParam(name = "id") @NotNull(message = "应用配置id不能为空") Long id){
        Wrapper wrapper = appConfigControllerClient.delAppConfig(id);
        return wrapper;
    }

    @ApiOperation(value="根据id更新应用配置",response = Boolean.class)
    @RequestMapping(value = "/updateAppConfig",method = RequestMethod.POST)
    public Object updateAppConfig(@Validated @RequestBody UpdateAppConfigDto updateAppConfigDto){
        Wrapper wrapper = appConfigControllerClient.updateAppConfig(updateAppConfigDto);
        return wrapper;
    }

    @ApiOperation(value="查询应用配置",response = AppConfig.class)
    @RequestMapping(value = "/queryAppConfig",method = RequestMethod.GET)
    public Object queryAppConfig(@RequestParam(name = "id") @NotNull(message = "应用配置id不能为空") Long id){
        Wrapper<AppConfig> appConfig = appConfigControllerClient.queryAppConfig(id);
        return appConfig;
    }

    @ApiOperation(value="查询应用配置列表",response = AppConfig.class)
    @RequestMapping(value ="/queryAppConfigList",method = RequestMethod.POST)
    public Object queryAppConfigList(@Validated @RequestBody AppConfigQueryDto appConfigQueryDto){
        Wrapper<List<AppConfig>> appConfigList = appConfigControllerClient.queryAppConfigList(appConfigQueryDto);
        return appConfigList;
    }

    @ApiOperation(value="分页查询应用配置列表",response = PageInfo.class)
    @RequestMapping(value ="/queryAppConfigListPage",method = RequestMethod.POST)
    public Object queryAppConfigListPage(@Validated @RequestBody AppConfigQueryDto appConfigQueryDto){
        Wrapper<PageInfo<AppConfig>> appConfigListPage = appConfigControllerClient.queryAppConfigListPage(appConfigQueryDto);
        return appConfigListPage;
    }

}
