package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.dto.AddSystemAppDto;
import com.bdxh.appmarket.dto.ModifySystemAppDto;
import com.bdxh.appmarket.dto.QuerySystemAppDto;
import com.bdxh.appmarket.entity.SystemApp;
import com.bdxh.appmarket.feign.SystemAppControllerClient;
import com.bdxh.appmarket.vo.appVersionVo;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class SystemAppControllerClientFallback implements SystemAppControllerClient {
    @Override
    public Wrapper addAppCategory(AddSystemAppDto addSystemAppDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSystemAppById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyAppCategory(ModifySystemAppDto modifySystemAppDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SystemApp>> findAppControl(QuerySystemAppDto querySystemAppDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<appVersionVo> versionUpdating() {
        return WrapMapper.error();
    }
}
