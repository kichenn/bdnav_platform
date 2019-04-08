package com.bdxh.system.persistence;

import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.dto.UserPermissionDto;
import com.bdxh.system.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PermissionMapper extends Mapper<Permission> {

    //角色id查询节点
    List<Permission> findPermissionByRoleId(@Param("roleId") Long roleId, @Param("type") Byte type);

    //用户id查询节点
    List<Permission> findPermissionByUserId(@Param("userId") Long userId);

    //批量删除
    Integer batchDelPermissionInIds(@Param("ids") List<Long> ids);

    //查询所有菜单 /选中状态 默认给2 未选中显示全部
    List<RolePermissionDto> theTreeMenu(@Param("roleId") Long roleId,@Param("selected")Integer selected);

    //角色id所有权限
    List<Permission> findPermission(@Param("roleId") Long roleId,@Param("selected")Integer selected);

    //根据菜单名称查询列表
    List<Permission> findByTitle(@Param("title") String title);

    //父级id查询部门信息
    Permission findPermissionByParentId(Long parentId);

    //根据用户id查询所有权限
    List<UserPermissionDto> findUserRights(@Param("userId") Long userId, @Param("type") Byte type);

}