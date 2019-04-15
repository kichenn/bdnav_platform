package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.dto.AddAppDto;
import com.bdxh.appmarket.dto.AppQueryDto;
import com.bdxh.appmarket.dto.UpdateAppDto;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 应用管理feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 17:11
 **/
@Component
public class AppControllerClientFallback implements AppControllerClient {


    @Override
    public Wrapper addApp(AddAppDto addAppDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delApp(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateApp(UpdateAppDto updateAppDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<App> queryApp(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<String> queryAppAndImages(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<App>> queryAppList(AppQueryDto appQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<App>> queryAppListPage(AppQueryDto appQueryDto) {
        return WrapMapper.error();
    }
}