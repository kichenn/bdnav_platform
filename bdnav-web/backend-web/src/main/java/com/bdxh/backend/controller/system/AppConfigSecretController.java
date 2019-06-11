package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigSecretDto;
import com.bdxh.system.dto.AppConfigSecretQueryDto;
import com.bdxh.system.dto.UpdateAppConfigSecretDto;
import com.bdxh.system.entity.AppConfigSecret;
import com.bdxh.system.feign.AppConfigSecretControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.ValueExp;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 控制器
 * @author: xuyuan
 * @create: 2019-03-21 17:14
 **/
@RestController
@RequestMapping("/appConfigSecretWeb")
@Validated
@Slf4j
@Api(value = "应用秘钥管理", tags = "应用秘钥管理")
public class AppConfigSecretController {

    @Autowired
    private AppConfigSecretControllerClient appConfigSecretControllerClient;

    @ApiOperation(value="增加应用秘钥",response = Boolean.class)
    @RequestMapping(value = "/addAppConfigSecret",method = RequestMethod.POST)
    public Object addAppConfigSecret(@Validated @RequestBody AddAppConfigSecretDto addAppConfigSecretDto){
        Wrapper wrapper = appConfigSecretControllerClient.addAppConfigSecret(addAppConfigSecretDto);
        return wrapper;
    }

    @ApiOperation(value="根据id删除应用秘钥",response = Boolean.class)
    @RequestMapping(value = "/delAppConfigSecret",method = RequestMethod.GET)
    public Object delAppConfigSecret(@RequestParam(name = "id") @NotNull(message = "应用秘钥id不能为空") Long id){
        Wrapper wrapper = appConfigSecretControllerClient.delAppConfigSecret(id);
        return wrapper;
    }

    @ApiOperation(value="根据id更新应用",response = Boolean.class)
    @RequestMapping(value = "/updateAppConfigSecret",method = RequestMethod.POST)
    public Object updateAppConfigSecret(@Valid @RequestBody UpdateAppConfigSecretDto updateAppConfigSecretDto){
        Wrapper wrapper = appConfigSecretControllerClient.updateAppConfigSecret(updateAppConfigSecretDto);
        return wrapper;
    }

    @ApiOperation(value="查询应用秘钥",response = AppConfigSecret.class)
    @RequestMapping(value = "/queryAppConfigSecret",method = RequestMethod.GET)
    public Object queryAppConfigSecret(@RequestParam(name = "id") @NotNull(message = "应用秘钥id不能为空") Long id){
        Wrapper<AppConfigSecret> appConfigSecret = appConfigSecretControllerClient.queryAppConfigSecret(id);
        return appConfigSecret;
    }

    @ApiOperation(value="查询应用秘钥列表",response = AppConfigSecret.class)
    @RequestMapping(value = "/queryAppConfigSecretList",method = RequestMethod.POST)
    public Object queryAppConfigSecretList(@Validated @RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto){
        Wrapper<List<AppConfigSecret>> queryAppConfigSecretList = appConfigSecretControllerClient.queryAppConfigSecretList(appConfigSecretQueryDto);
        return queryAppConfigSecretList;
    }

    @ApiOperation(value="分页查询应用列表",response = PageInfo.class)
    @RequestMapping(value = "/queryAppConfigSecretListPage",method = RequestMethod.POST)
    public Object queryAppConfigSecretListPage(@Validated @RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto){
        Wrapper<PageInfo<AppConfigSecret>> appConfigSecretListPage = appConfigSecretControllerClient.queryAppConfigSecretListPage(appConfigSecretQueryDto);
        return appConfigSecretListPage;
    }

}
