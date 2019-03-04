package com.bdxh.system.persistence;

import com.bdxh.system.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PermissionMapper extends Mapper<Permission> {

    //角色id查询父节点
    List<Permission> findPermissionByRoleId(@Param("roleId") Long roleId, @Param("type") Byte type);

    //批量删除
    Integer batchDelPermissionInIds(@Param("ids") List<Long> ids);
}