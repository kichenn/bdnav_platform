package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.dto.AddAppVersionDto;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.feign.AppVersionControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-14 18:11
 **/
@Component
public class AppVersionControllerClientFallback implements AppVersionControllerClient {
    @Override
    public Wrapper addAppVersion(AddAppVersionDto addAppVersionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppVersion>> findAppVersion(Long appId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppVersion> findNewAppVersion(Long appId) {
        return WrapMapper.error();
    }
}