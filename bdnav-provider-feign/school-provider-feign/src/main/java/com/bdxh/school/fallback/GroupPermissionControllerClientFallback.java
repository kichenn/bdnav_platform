package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddGroupPermissionDto;
import com.bdxh.school.dto.GroupPermissionQueryDto;
import com.bdxh.school.dto.ModifyGroupPermissionDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.feign.GroupPermissionControllerClient;
import com.bdxh.school.vo.GroupPermissionInfoVo;
import com.bdxh.school.vo.GroupPermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校门禁组feign降级服务
 * @Author: Kang
 * @Date: 2019/3/27 16:43
 */
@Component
public class GroupPermissionControllerClientFallback implements GroupPermissionControllerClient {
    @Override
    public Wrapper addGroupPermission(AddGroupPermissionDto addGroupPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyGroupPermission(ModifyGroupPermissionDto modifyGroupPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delGroupPermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchGroupPermissionInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<GroupPermissionInfoVo> findGroupPermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<GroupPermission> findGroupByGroupIdsAndType(Long groupId, GroupTypeEnum groupTypeEnum) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<GroupPermissionShowVo>> findGroupPermissionInConditionPage(GroupPermissionQueryDto groupPermissionQueryDto) {
        return WrapMapper.error();
    }
}
