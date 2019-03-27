package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddAppConfigSecretDto;
import com.bdxh.system.dto.AppConfigSecretQueryDto;
import com.bdxh.system.dto.UpdateAppConfigSecretDto;
import com.bdxh.system.entity.AppConfigSecret;
import com.bdxh.system.feign.AppConfigSecretControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @description: 应用秘钥管理feign降级服务
 * @author: xuyuan
 * @create: 2019-03-27 16:28
 **/
@Component
public class AppConfigSecretControllerClientFallback implements AppConfigSecretControllerClient {

    @Override
    public Wrapper addAppConfigSecret(AddAppConfigSecretDto addAppConfigSecretDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delAppConfigSecret(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateAppConfigSecret(UpdateAppConfigSecretDto updateAppConfigSecretDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppConfigSecret> queryAppConfigSecret(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppConfigSecret>> queryAppConfigSecretList(AppConfigSecretQueryDto appConfigSecretQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<AppConfigSecret>> queryAppConfigSecretListPage(AppConfigSecretQueryDto appConfigSecretQueryDto) {
        return WrapMapper.error();
    }

}
