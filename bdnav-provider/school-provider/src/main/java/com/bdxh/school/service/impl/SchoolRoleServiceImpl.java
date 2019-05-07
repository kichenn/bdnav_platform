package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.persistence.SchoolRoleMapper;
import com.bdxh.school.persistence.SchoolRolePermissionMapper;
import com.bdxh.school.persistence.SchoolUserRoleMapper;
import com.bdxh.school.service.SchoolRoleService;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description: 角色管理service实现
 * @Author: Kang
 * @Date: 2019/3/26 12:23
 */
@Service
@Slf4j
public class SchoolRoleServiceImpl extends BaseService<SchoolRole> implements SchoolRoleService {

    @Autowired
    private SchoolRoleMapper schoolRoleMapper;

    @Autowired
    private SchoolUserRoleMapper schoolUserRoleMapper;

    @Autowired
    private SchoolRolePermissionMapper schoolRolePermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(Long roleId) {
        //删除角色
        schoolRoleMapper.deleteByPrimaryKey(roleId);
        //删除角色与用户的捆绑关系
        schoolUserRoleMapper.deleteByRoleId(roleId);
        //删除角色和权限的捆绑关系
        schoolRolePermissionMapper.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBatchRole(List<Long> roleIds) {
        schoolRoleMapper.delBatchRoleByRoleIds(roleIds);
        schoolUserRoleMapper.delBatchRoleByRoleIds(roleIds);
        schoolRolePermissionMapper.delBatchRoleByRoleIds(roleIds);
    }

    @Override
    public PageInfo<SchoolRoleShowVo> findListPage(SchoolRoleQueryDto roleQueryDto) {
        PageHelper.startPage(roleQueryDto.getPageNum(), roleQueryDto.getPageSize());
        SchoolRole schoolRole = new SchoolRole();
        BeanUtils.copyProperties(roleQueryDto, schoolRole);
        List<SchoolRoleShowVo> roleLogs = schoolRoleMapper.getByCondition(schoolRole);
        PageInfo<SchoolRoleShowVo> pageInfo = new PageInfo<>(roleLogs);
        return pageInfo;
    }

    @Override
    public List<String> getRoleListByUserId(Long userId) {
        List<String> roles = schoolRoleMapper.getRoleListByUserId(userId);
        return roles;
    }

    @Override
    public List<Long> getRoleIdListByUserId(Long userId) {
        List<Long> roles = schoolRoleMapper.getRoleIdListByUserId(userId);
        return roles;
    }

    @Override
    public List<Map<Long, String>> findRoleByUserIdResultMap(Long userId) {
        return schoolRoleMapper.findRoleByUserIdResultMap(userId);
    }

    @Override
    public SchoolRole getRoleByRole(String role) {
        return schoolRoleMapper.getRoleByRole(role);
    }

    @Override
    public PageInfo<SchoolRole> findRolesInConditionPaging(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<SchoolRole> roles = schoolRoleMapper.selectAll();
        PageInfo<SchoolRole> pageInfo = new PageInfo(roles);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


}
