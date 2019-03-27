package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigSecretDto;
import com.bdxh.system.dto.AppConfigSecretQueryDto;
import com.bdxh.system.dto.UpdateAppConfigSecretDto;
import com.bdxh.system.entity.AppConfigSecret;
import com.bdxh.system.fallback.AppConfigSecretControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: 应用秘钥管理feign客户端
 * @author: xuyuan
 * @create: 2019-03-27 16:28
 **/
@Service
@FeignClient(value = "system-provider-cluster",fallback= AppConfigSecretControllerClientFallback.class)
public interface AppConfigSecretControllerClient {

    @RequestMapping("/appConfigSecret/addAppConfigSecret")
    @ResponseBody
    Wrapper addAppConfigSecret(@RequestBody AddAppConfigSecretDto addAppConfigSecretDto);

    @RequestMapping("/appConfigSecret/delAppConfigSecret")
    @ResponseBody
    Wrapper delAppConfigSecret(@RequestParam(name = "id") Long id);

    @RequestMapping("/appConfigSecret/updateAppConfigSecret")
    @ResponseBody
    Wrapper updateAppConfigSecret(@RequestBody UpdateAppConfigSecretDto updateAppConfigSecretDto);

    @RequestMapping("/appConfigSecret/queryAppConfigSecret")
    @ResponseBody
    Wrapper<AppConfigSecret> queryAppConfigSecret(@RequestParam(name = "id") Long id);

    @RequestMapping("/appConfigSecret/queryAppConfigSecretList")
    @ResponseBody
    Wrapper<List<AppConfigSecret>> queryAppConfigSecretList(@RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto);

    @RequestMapping("/appConfigSecret/queryAppConfigSecretListPage")
    @ResponseBody
    Wrapper<PageInfo<AppConfigSecret>> queryAppConfigSecretListPage(@RequestBody AppConfigSecretQueryDto appConfigSecretQueryDto);

}
