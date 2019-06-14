package com.bdxh.servicepermit.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.servicepermit.entity.ServiceRole;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Repository
public interface ServiceRoleMapper extends Mapper<ServiceRole> {

    /**
     * 查询总条数
     */
    Integer getServiceRoleAllCount();

    /**
     * 批量删除方法
     */
    Integer delServiceRoleInIds(@Param("ids") List<Long> ids);

    /**
     * 条件查询列表
     *
     * @param serviceRole
     * @return
     */
    List<ServiceRole> queryServiceRole(@Param("serviceRole") ServiceRole serviceRole);

    ServiceRole findServiceRoleByName(@Param("name") String name);
}
