package com.bdxh.appmarket.feign;

import com.bdxh.appmarket.dto.AddAppVersionDto;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.fallback.AppVersionControllerClientFallback;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-14 18:11
 **/
@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = AppVersionControllerClientFallback.class)
public interface AppVersionControllerClient {

    @ResponseBody
    @RequestMapping(value = "/appVersion/addAppVersion",method = RequestMethod.POST)
    Wrapper addAppVersion(@RequestBody AddAppVersionDto addAppVersionDto);
    /**
     * 查看APP版本历史
     */
    @ResponseBody
    @RequestMapping(value = "/appVersion/findAppVersion",method = RequestMethod.GET)
    Wrapper<List<AppVersion>> findAppVersion(@RequestParam("appId")Long appId);

    /**
     * 查看最新APP版本
     */
    @ResponseBody
    @RequestMapping(value = "/appVersion/findNewAppVersion",method = RequestMethod.GET)
    Wrapper<AppVersion> findNewAppVersion(@RequestParam("appId")Long appId);
}