package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.feign.DeptControllerClient;
import com.bdxh.system.vo.DeptDetailsVo;
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
    public Wrapper<List<DeptTreeVo>> queryDeptList(DeptQueryDto deptQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<DeptDetailsVo> findDeptByParentId(Long deptId, Long parentId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addDept(DeptDto deptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateDept(DeptDto deptDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delDept(Long deptId) {
        return WrapMapper.error();
    }






}
