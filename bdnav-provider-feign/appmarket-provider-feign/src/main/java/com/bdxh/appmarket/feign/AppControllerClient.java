package com.bdxh.appmarket.feign;

import com.bdxh.appmarket.dto.AddAppDto;
import com.bdxh.appmarket.dto.AppQueryDto;
import com.bdxh.appmarket.dto.UpdateAppDto;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.fallback.AppControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 应用管理feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 17:11
 **/
@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = AppControllerClientFallback.class)
public interface AppControllerClient {

    @RequestMapping("/app/addApp")
    Wrapper addCategory(@RequestBody AddAppDto addAppDto);

    @RequestMapping("/app/delApp")
    Wrapper delApp(@RequestParam(name = "id") Long id);

    @RequestMapping("/app/updateApp")
    Wrapper updateApp(@RequestBody UpdateAppDto updateAppDto);

    @RequestMapping("/app/queryApp")
    Wrapper<App> queryApp(@RequestParam(name = "id") Long id);

    @RequestMapping("/app/queryAppAndImages")
    Wrapper<String> queryAppAndImages(@RequestParam(name = "id") Long id);

    @RequestMapping("/app/queryAppList")
    Wrapper<List<App>> queryAppList(@RequestBody AppQueryDto appQueryDto);

    @RequestMapping("/queryAppListPage")
    Wrapper<PageInfo<App>> queryAppListPage(@RequestBody AppQueryDto appQueryDto);

}
