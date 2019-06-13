package com.bdxh.servicepermit.service;

import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Service
public interface ServiceRoleService extends IService<ServiceRole> {

    /**
     * 查询所有数量
     */
    Integer getServiceRoleAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelServiceRoleInIds(List<Long> id);

    /**
     * 分页查询权限角色信息
     *
     * @param serviceRoleQueryDto
     * @return
     */
    PageInfo<ServiceRole> queryServiceRole(ServiceRoleQueryDto serviceRoleQueryDto);

    /**
     * 角色名称查询角色信息
     */
    ServiceRole findServiceRoleByName(String name);
}
