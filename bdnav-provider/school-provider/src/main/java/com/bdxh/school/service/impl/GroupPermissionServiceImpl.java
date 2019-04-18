package com.bdxh.school.service.impl;

import com.bdxh.school.dto.GroupPermissionQueryDto;
import com.bdxh.school.service.GroupPermissionService;
import com.bdxh.school.vo.GroupPermissionShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.persistence.GroupPermissionMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-03-27 12:26:55
 */
@Service
@Slf4j
public class GroupPermissionServiceImpl extends BaseService<GroupPermission> implements GroupPermissionService {

    @Autowired
    private GroupPermissionMapper groupPermissionMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getGroupPermissionAllCount() {
        return groupPermissionMapper.getGroupPermissionAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelGroupPermissionInIds(List<Long> ids) {
        return groupPermissionMapper.delGroupPermissionInIds(ids) > 0;
    }


    /**
     * 部门id+部门类型查询学校组门禁
     */
    @Override
    public GroupPermission findGroupRecursionPermissionIds(Long groupId, Byte groupType) {
        return groupPermissionMapper.findGroupRecursionPermissionIds(groupId, groupType);
    }


    /**
     * 分页查询学校组门禁信息
     */
    @Override
    public PageInfo<GroupPermissionShowVo> findGroupPermissionInConditionPage(GroupPermissionQueryDto groupPermissionQueryDto) {
        Page page = PageHelper.startPage(groupPermissionQueryDto.getPageNum(), groupPermissionQueryDto.getPageSize());
        GroupPermission groupPermission = new GroupPermission();
        BeanUtils.copyProperties(groupPermissionQueryDto, groupPermission);
        //设置类型
        if (groupPermissionQueryDto.getGroupTypeEnum() != null) {
            groupPermission.setGroupType(groupPermissionQueryDto.getGroupTypeEnum().getKey());
        }
        PageInfo<GroupPermissionShowVo> pageInfo = new PageInfo(groupPermissionMapper.findGroupPermissionInConditionPage(groupPermission));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


}
