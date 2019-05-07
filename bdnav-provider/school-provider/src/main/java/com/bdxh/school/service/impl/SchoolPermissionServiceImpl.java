package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.entity.SchoolPermission;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.persistence.SchoolPermissionMapper;
import com.bdxh.school.service.SchoolPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 权限操作service
 * @Author: Kang
 * @Date: 2019/3/26 12:18
 */
@Service
@Slf4j
public class SchoolPermissionServiceImpl extends BaseService<SchoolPermission> implements SchoolPermissionService {

    @Autowired
    private SchoolPermissionMapper schoolPermissionMapper;

    //权限类型（type：1菜单，2按钮）
    private static final Byte permissionType = new Byte("1");

    //学校id+角色id 菜单类型 筛选查询权限菜单
    @Override
    public List<SchoolPermission> findPermissionByRoleId(List<Long> roleId, Byte type, Long schoolId) {
        return schoolPermissionMapper.findPermissionByRoleId(roleId, type, schoolId);
    }


    //增加权限列表信息
    @Override
    public Boolean addPermission(SchoolPermission permission) {
        if (new Long("-1").equals(permission.getParentId())) {
            permission.setParentIds("");
//            permission.setPath("/" + permission.getName());
        } else {
            //查询父亲节点
            SchoolPermission schoolPermission = findSchoolPermissionById(permission.getParentId());
            permission.setParentIds(schoolPermission.getParentIds() + "," + schoolPermission.getId());
//            permission.setPath(schoolPermission.getPath() + "/" + schoolPermission.getName() + "/" + permission.getName());
        }
        permission.setPath("/" + permission.getName());
        return schoolPermissionMapper.insertSelective(permission) > 0;
    }

    //修改权限列表信息
    @Override
    public Boolean modifyPermission(SchoolPermission permission) {
        if (new Long("-1").equals(permission.getParentId())) {
            permission.setParentIds("");
//            permission.setPath("/" + permission.getName());
        } else {
            //查询父亲节点
            SchoolPermission schoolPermission = findSchoolPermissionById(permission.getParentId());
            permission.setParentIds(schoolPermission.getParentIds() + "," + schoolPermission.getId());
//            permission.setPath(schoolPermission.getPath() + "/" + schoolPermission.getName() + "/" + permission.getName());
        }
        permission.setPath("/" + permission.getName());
        return schoolPermissionMapper.updateByPrimaryKeySelective(permission) > 0;
    }

    //Id删除权限列表信息
    @Override
    public Boolean delPermissionById(Long id) {
        return schoolPermissionMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除权限列表信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelPermission(List<Long> ids) {
        return schoolPermissionMapper.batchDelPermissionInIds(ids) > 0;
    }

    //根据id查询菜单或者按钮信息
    @Override
    public SchoolPermission findSchoolPermissionById(Long id) {
        return schoolPermissionMapper.selectByPrimaryKey(id);
    }

    //根据parentId查询子菜单
    @Override
    public List<SchoolPermission> findSchoolPermissionByParentId(Long parentId) {
        SchoolPermission schoolPermission = new SchoolPermission();
        schoolPermission.setParentId(parentId);
        return schoolPermissionMapper.select(schoolPermission);
    }

    //用户id查询权限菜单
    @Override
    public List<String> permissionMenusByUserId(Long userId) {
        List<String> permissionMenus = new ArrayList<>();
        List<SchoolPermission> permissions = schoolPermissionMapper.findPermissionByUserId(userId);
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                permissionMenus.add(e.getName());
            });
        }
        return permissionMenus;
    }
}
