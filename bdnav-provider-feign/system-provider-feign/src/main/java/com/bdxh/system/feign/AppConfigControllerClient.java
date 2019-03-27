package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigDto;
import com.bdxh.system.dto.AppConfigQueryDto;
import com.bdxh.system.dto.UpdateAppConfigDto;
import com.bdxh.system.entity.AppConfig;
import com.bdxh.system.fallback.AppConfigControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 应用配置feign客户端
 * @author: xuyuan
 * @create: 2019-03-27 16:28
 **/
@Service
@FeignClient(value = "system-provider-cluster",fallback= AppConfigControllerClientFallback.class)
public interface AppConfigControllerClient {

    @RequestMapping("/appConfig/addAppConfig")
    @ResponseBody
    Wrapper addAppConfig(@Valid @RequestBody AddAppConfigDto addAppConfigDto);

    @RequestMapping("/appConfig/delAppConfig")
    @ResponseBody
    Wrapper delAppConfig(@RequestParam(name = "id") Long id);

    @RequestMapping("/appConfig/updateAppConfig")
    @ResponseBody
    Wrapper updateAppConfig(@RequestBody UpdateAppConfigDto updateAppConfigDto);

    @RequestMapping("/appConfig/queryAppConfig")
    @ResponseBody
    Wrapper<AppConfig> queryAppConfig(@RequestParam(name = "id") Long id);

    @RequestMapping("/appConfig/queryAppConfigList")
    @ResponseBody
    Wrapper<List<AppConfig>> queryAppConfigList(@RequestBody AppConfigQueryDto appConfigQueryDto);

    @RequestMapping("/appConfig/queryAppConfigListPage")
    Wrapper<PageInfo<AppConfig>> queryAppConfigListPage(@Valid @RequestBody AppConfigQueryDto appConfigQueryDto);

}
