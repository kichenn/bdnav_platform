package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.feign.DeptControllerClient;
import com.bdxh.system.vo.DeptTreeVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统部门feign降级
 */
@Component
public class DeptControllerClientFallback implements DeptControllerClient {
    @Override
    public Wrapper<List<DeptTreeVo>> queryDeptTreeById(Long deptId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryDeptList(DeptQueryDto deptQueryDto) {
            return WrapMapper.error();
    }
}
