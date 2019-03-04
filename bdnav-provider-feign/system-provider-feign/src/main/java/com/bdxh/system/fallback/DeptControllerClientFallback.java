package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.feign.DeptControllerClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统部门feign降级
 */
@Component
public class DeptControllerClientFallback implements DeptControllerClient {
    @Override
    public Wrapper<List<String>> queryDeptTreeById(Long deptId) {
        return WrapMapper.error();
    }
}
