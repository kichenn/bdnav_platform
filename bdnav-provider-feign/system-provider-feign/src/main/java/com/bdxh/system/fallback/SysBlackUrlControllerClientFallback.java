package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.dto.AddSysBlackUrlDto;
import com.bdxh.system.feign.SysBlackUrlControllerClient;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WanMing
 * @date 2019/6/20 16:16
 */
@Component
public class SysBlackUrlControllerClientFallback implements SysBlackUrlControllerClient {
    @Override
    public Wrapper addSysBlackUrl(AddSysBlackUrlDto addSysBlackUrlDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSysBlackUrl(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchDelSysBlackUrl(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo> findSysBlackUrlByCondition(SysBlackUrlQueryDto sysBlackUrlQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SysBlackUrlVo>> queryAllSysBlackUrl() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchCheckSysBlackUrl(List<String> urls) {
        return WrapMapper.error();
    }
}
