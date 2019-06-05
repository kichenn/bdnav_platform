package com.bdxh.appmarket.feign;

import com.bdxh.appmarket.dto.*;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.fallback.AppControllerClientFallback;
import com.bdxh.appmarket.vo.appDownloadlinkVo;
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
    @ResponseBody
    Wrapper addApp(@RequestBody AddAppDto addAppDto);

    @RequestMapping(value = "/app/delApp",method = RequestMethod.POST)
    @ResponseBody
    Wrapper delApp(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/updateApp",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateApp(@RequestBody UpdateAppDto updateAppDto);

    @RequestMapping(value = "/app/queryApp",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<App> queryApp(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/queryAppAndImages",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<String> queryAppAndImages(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/app/queryAppList",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<App>> queryAppList(@RequestBody AppQueryDto appQueryDto);

    @RequestMapping(value = "/app/queryAppListPage",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<App>> queryAppListPage(@RequestBody AppQueryDto appQueryDto);

    @RequestMapping(value = "/app/getApplicationOfCollection",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<App>> getApplicationOfCollection(@RequestBody QueryAppDto queryAppDto);

    @RequestMapping(value = "/app/getAppListByids",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<App>> getAppListByids(@RequestParam(name = "ids")String ids);


    @RequestMapping(value = "/app/familyFindAppInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<App>> familyFindAppInfo(@RequestParam("schoolCode") String schoolCode);

    @RequestMapping(value = "/app/pushInstallApps",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<Boolean> pushInstallApps(@RequestParam("id") Long id,
                                     @RequestParam("userName")String userName,
                                     @RequestParam("cardNumber")String cardNumber,
                                     @RequestParam("clientId")String clientId);


    @RequestMapping(value = "/app/thePresetList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<appDownloadlinkVo>> thePresetList(@RequestParam("preset") Byte preset);


    @RequestMapping(value = "/app/findTheApplicationList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<appDownloadlinkVo>> findTheApplicationList(@RequestParam("schoolCode") String schoolCode);


}
