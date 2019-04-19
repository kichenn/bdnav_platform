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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 应用管理feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 17:11
 **/
@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = AppControllerClientFallback.class)
public interface AppControllerClient {

    @RequestMapping(value = "/app/addApp",method = RequestMethod.POST)
    Wrapper addApp(@RequestBody AddAppDto addAppDto);

    @RequestMapping(value = "/app/delApp",method = RequestMethod.POST)
    Wrapper delApp(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/updateApp",method = RequestMethod.POST)
    Wrapper updateApp(@RequestBody UpdateAppDto updateAppDto);

    @RequestMapping(value = "/app/queryApp",method = RequestMethod.GET)
    Wrapper<App> queryApp(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/queryAppAndImages",method = RequestMethod.GET)
    Wrapper<String> queryAppAndImages(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/queryAppList",method = RequestMethod.POST)
    Wrapper<List<App>> queryAppList(@RequestBody AppQueryDto appQueryDto);

    @RequestMapping(value = "/app/queryAppListPage",method = RequestMethod.POST)
    Wrapper<PageInfo<App>> queryAppListPage(@RequestBody AppQueryDto appQueryDto);

    @RequestMapping(value = "/app/getApplicationOfCollection",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<App>> getApplicationOfCollection(@RequestBody AppQueryDto appQueryDto);
}
