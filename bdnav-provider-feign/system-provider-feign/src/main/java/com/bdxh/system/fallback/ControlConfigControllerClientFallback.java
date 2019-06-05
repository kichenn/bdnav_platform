package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.dto.QueryControlConfig;
import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.feign.ControlConfigControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControlConfigControllerClientFallback implements ControlConfigControllerClient {
    @Override
    public Wrapper addControlConfig(AddControlConfig addControlConfig) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyControlConfig(ModifyControlConfig modifyControlConfig) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delControlConfig(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> findAppType(Byte appType) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ControlConfig>> queryListPage(QueryControlConfig queryControlConfig) {
        return WrapMapper.error();
    }
}
