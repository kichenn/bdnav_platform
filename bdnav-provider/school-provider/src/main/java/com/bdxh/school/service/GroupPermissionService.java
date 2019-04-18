package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.GroupPermissionQueryDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.vo.GroupPermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-03-27 12:21:12
 */
@Service
public interface GroupPermissionService extends IService<GroupPermission> {

    /**
     * 查询所有数量
     */
    Integer getGroupPermissionAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelGroupPermissionInIds(List<Long> id);

    /**
     * 部门id+部门类型查询学校组门禁
     */
    GroupPermission findGroupRecursionPermissionIds(Long groupId, Byte groupType);


    /**
     * 分页查询学校组门禁信息
     */
    PageInfo<GroupPermissionShowVo> findGroupPermissionInConditionPage(GroupPermissionQueryDto groupPermissionQueryDto);
}
