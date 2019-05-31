package com.bdxh.appburied.fallback;

import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.appburied.dto.ModifyInstallAppsDto;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Component
public class InstallAppsControllerClientFallback implements InstallAppsControllerClient {

    @Override
    public Wrapper addInstallApp(AddInstallAppsDto addInstallAppsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyInstallApp(ModifyInstallAppsDto modifyInstallAppsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delInstallAppById(DelOrFindAppBuriedDto delInstallAppsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<InstallApps> findInstallAppById(DelOrFindAppBuriedDto findInstallAppsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<InstallApps>> findInstallAppsInContionPaging(InstallAppsQueryDto installAppsQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<InstallApps>> findInstallAppsInConation(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchSaveInstallAppsInfo(List<AddInstallAppsDto> appInstallList) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delByAppPackage(String schoolCode, String cardNumber, String appPackage) {
        return WrapMapper.error();
    }
}