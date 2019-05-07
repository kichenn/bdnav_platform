package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolPermissionMapper extends Mapper<SchoolPermission> {

    //角色id查询父节点
    List<SchoolPermission> findPermissionByRoleId(@Param("roleIds") List<Long> roleId, @Param("type") Byte type, @Param("schoolId") Long schoolId);

    //批量删除
    Integer batchDelPermissionInIds(@Param("ids") List<Long> ids);

    //角色id所有权限
    List<SchoolPermission> findPermission(@Param("roleId") Long roleId);

    //用户id查询节点
    List<SchoolPermission> findPermissionByUserId(@Param("userId") Long userId);
}