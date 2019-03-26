package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUserRole;
import com.bdxh.school.persistence.SchoolUserRoleMapper;
import com.bdxh.school.service.SchoolUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description:   用户角色管理service实现
* @Author: Kang
* @Date: 2019/3/26 12:29
*/
@Service
@Slf4j
public class SchoolUserRoleServiceImpl extends BaseService<SchoolUserRole> implements SchoolUserRoleService {

    @Autowired
    private SchoolUserRoleMapper schoolUserRoleMapper;

    @Override
    public List<SchoolRole> findUserRole(Long RoleId) {
        return schoolUserRoleMapper.findUserRole(RoleId);
    }
}
