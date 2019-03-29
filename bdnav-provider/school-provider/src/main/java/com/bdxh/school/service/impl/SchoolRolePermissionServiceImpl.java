package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.entity.SchoolRolePermission;
import com.bdxh.school.persistence.SchoolRolePermissionMapper;
import com.bdxh.school.service.SchoolRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @Description: 角色权限关系service
 * @Author: Kang
 * @Date: 2019/3/1 9:57
 */
@Service
@Slf4j
public class SchoolRolePermissionServiceImpl extends BaseService<SchoolRolePermission> implements SchoolRolePermissionService {

    @Autowired
    private SchoolRolePermissionMapper schoolRolePermissionMapper;

    // 增加角色与权限关系
    @Override
    public Boolean addRolePermission(SchoolRolePermission rolePermission) {
        return schoolRolePermissionMapper.insertSelective(rolePermission) > 0;
    }

    //角色id查询所有权限id
    @Override
    public List<SchoolRolePermission> findPermissionId(Long roleId) {
        return schoolRolePermissionMapper.findPermissionId(roleId);
    }

    //角色id查询所有权限id
    @Override
    public List<Long> findPermissionIdByRoleId(Long roleId) {
        return schoolRolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

    // 删除角色与权限关系
    @Override
    public Boolean delRolePermission(Long roleId) {
        return schoolRolePermissionMapper.deleteByRoleId(roleId) > 0;
    }

    //角色与权限菜单的捆绑
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRolePermissionBindMenu(AddRolePermissionBindMenuDto addRolePermissionBindMenu) {
        //清除角色开始与菜单的捆绑
        schoolRolePermissionMapper.deleteByRoleId(addRolePermissionBindMenu.getRoleId());
        //增加此次角色与菜单的捆绑
        if (CollectionUtils.isNotEmpty(addRolePermissionBindMenu.getPermissionIds())) {
            //去重
            HashSet h = new HashSet(addRolePermissionBindMenu.getPermissionIds());
            List<Long> tempList = new ArrayList<>();
            tempList.addAll(h);
            //新增角色与菜单的捆绑
            tempList.stream().forEach(e -> {
                SchoolRolePermission schoolRolePermission = new SchoolRolePermission();
                schoolRolePermission.setRoleId(addRolePermissionBindMenu.getRoleId());
                schoolRolePermission.setPermissionId(e);
                schoolRolePermission.setSchoolCode(addRolePermissionBindMenu.getSchoolCode());
                schoolRolePermission.setSchoolId(addRolePermissionBindMenu.getSchoolId());
                schoolRolePermissionMapper.insertSelective(schoolRolePermission);
            });
        }
    }
}
