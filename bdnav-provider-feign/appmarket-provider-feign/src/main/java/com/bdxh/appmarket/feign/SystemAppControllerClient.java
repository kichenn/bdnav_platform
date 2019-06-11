package com.bdxh.appmarket.feign;


import com.bdxh.appmarket.dto.*;
import com.bdxh.appmarket.entity.SystemApp;
import com.bdxh.appmarket.fallback.SystemAppControllerClientFallback;
import com.bdxh.appmarket.vo.appVersionVo;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = SystemAppControllerClientFallback.class)
public interface SystemAppControllerClient {


    @RequestMapping(value = "/systemApp/addAppCategory",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addAppCategory(@RequestBody AddSystemAppDto addSystemAppDto);

    @RequestMapping(value = "/systemApp/delSystemAppById",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSystemAppById(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/systemApp/modifyAppCategory",method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyAppCategory(@RequestBody ModifySystemAppDto modifySystemAppDto);

    @RequestMapping(value = "/systemApp/findAppControl",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SystemApp>> findAppControl(@RequestBody QuerySystemAppDto querySystemAppDto);

    @RequestMapping(value = "/systemApp/versionUpdating",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<appVersionVo> versionUpdating();
}
