package com.bdxh.appburied.feign;

import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.appburied.dto.ModifyInstallAppsDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.fallback.InstallAppsControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@FeignClient(value = "appburied-provider-cluster",fallback = InstallAppsControllerClientFallback.class)
public interface InstallAppsControllerClient {

    @RequestMapping(value = "/installApps/addInstallApp", method = RequestMethod.POST)
    Wrapper addInstallApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto);

    @RequestMapping(value = "/installApps/modifyInstallApp", method = RequestMethod.POST)
    Wrapper modifyInstallApp(@Validated @RequestBody ModifyInstallAppsDto modifyInstallAppsDto);

    @RequestMapping(value = "/installApps/delInstallAppById", method = RequestMethod.POST)
    Wrapper delInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto delInstallAppsDto);

    @RequestMapping(value = "/installApps/findInstallAppById", method = RequestMethod.POST)
    Wrapper<InstallApps> findInstallAppById(@Validated @RequestBody DelOrFindAppBuriedDto findInstallAppsDto);

    @RequestMapping(value = "/installApps/findInstallAppsInContionPaging", method = RequestMethod.POST)
    Wrapper<PageInfo<InstallApps>> findInstallAppsInContionPaging(@Validated @RequestBody InstallAppsQueryDto installAppsQueryDto);
}