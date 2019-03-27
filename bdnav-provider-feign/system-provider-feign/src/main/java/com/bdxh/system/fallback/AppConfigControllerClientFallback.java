package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigDto;
import com.bdxh.system.dto.AppConfigQueryDto;
import com.bdxh.system.dto.UpdateAppConfigDto;
import com.bdxh.system.entity.AppConfig;
import com.bdxh.system.feign.AppConfigControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 应用管理feign降级服务
 * @author: xuyuan
 * @create: 2019-03-27 16:30
 **/
@Component
public class AppConfigControllerClientFallback implements AppConfigControllerClient {

    @Override
    public Wrapper addAppConfig(@Valid AddAppConfigDto addAppConfigDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delAppConfig(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateAppConfig(UpdateAppConfigDto updateAppConfigDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppConfig> queryAppConfig(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppConfig>> queryAppConfigList(AppConfigQueryDto appConfigQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<AppConfig>> queryAppConfigListPage(@Valid AppConfigQueryDto appConfigQueryDto) {
        return WrapMapper.error();
    }

}
