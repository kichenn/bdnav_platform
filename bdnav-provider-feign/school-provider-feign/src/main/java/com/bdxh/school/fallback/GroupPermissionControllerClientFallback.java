package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校门禁组eign降级服务
 * @Author: Kang
 * @Date: 2019/3/27 16:43
 */
@Component
public class GroupPermissionControllerClientFallback implements SinglePermissionControllerClient {
    @Override
    public Wrapper addSinglePermission(SinglePermission singlePermission) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySinglePermission(SinglePermission singlePermission) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSinglePermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchSinglePermissionByInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SinglePermission> findSinglePermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<GroupPermission>> findSinglePermissionInConditionPage(SinglePermissionQueryDto singlePermissionQueryDto) {
        return WrapMapper.error();
    }
}
