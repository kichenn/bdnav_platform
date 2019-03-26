package com.bdxh.school.service;


import com.bdxh.common.support.IService;
import com.bdxh.school.entity.SchoolPermission;

import java.util.List;

/**
 * @Description: 学校角色权限
 * @Author: Kang
 * @Date: 2019/3/26 12:03
 */
public interface SchoolPermissionService extends IService<SchoolPermission> {

    //学校id+角色id，菜单类型 查询权限菜单or按钮
    List<SchoolPermission> findPermissionByRoleId(Long roleId, Byte type, Long schoolId);


    //增加权限列表信息
    Boolean addPermission(SchoolPermission permission);

    //修改权限列表信息
    Boolean modifyPermission(SchoolPermission permission);

    //Id删除权限列表信息
    Boolean delPermissionById(Long id);

    //批量删除权限列表信息
    Boolean batchDelPermission(List<Long> ids);

    //根据id查询权限信息
    SchoolPermission findSchoolPermissionById(Long id);
}
