package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUserRole;

import java.util.List;

/**
 * @Description: 学校用户角色管理service
 * @Author: Kang
 * @Date: 2019/3/26 12:12
 */
public interface SchoolUserRoleService extends IService<SchoolUserRole> {

    List<SchoolRole> findUserRole(Long RoleId);
}
