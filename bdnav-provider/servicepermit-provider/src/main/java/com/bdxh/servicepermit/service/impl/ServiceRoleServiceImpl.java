package com.bdxh.servicepermit.service.impl;

import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.service.ServiceRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.persistence.ServiceRoleMapper;

import java.util.List;


/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-05-31 11:36:26
 */
@Service
@Slf4j
public class ServiceRoleServiceImpl extends BaseService<ServiceRole> implements ServiceRoleService {

    @Autowired
    private ServiceRoleMapper serviceRoleMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getServiceRoleAllCount() {
        return serviceRoleMapper.getServiceRoleAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelServiceRoleInIds(List<Long> ids) {
        return serviceRoleMapper.delServiceRoleInIds(ids) > 0;
    }

    /**
     * 分页查询权限角色信息
     *
     * @param serviceRoleQueryDto
     * @return
     */
    @Override
    public PageInfo<ServiceRole> queryServiceRole(ServiceRoleQueryDto serviceRoleQueryDto) {
        Page page = PageHelper.startPage(serviceRoleQueryDto.getPageNum(), serviceRoleQueryDto.getPageSize());
        ServiceRole serviceRole = new ServiceRole();
        serviceRole.setName(serviceRoleQueryDto.getName());
        List<ServiceRole> serviceRoles = serviceRoleMapper.queryServiceRole(serviceRole);
        PageInfo<ServiceRole> pageInfo = new PageInfo<>(serviceRoles);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * 角色名称查询角色信息
     */
    @Override
    public ServiceRole findServiceRoleByName(String name) {
        return serviceRoleMapper.findServiceRoleByName(name);
    }
}
