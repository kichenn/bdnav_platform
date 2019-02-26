package com.bdxh.system.persistence;

import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserRoleMapper extends Mapper<UserRole> {
    //根据id查找对象列表
    List<Role> findUserRole( Long RoleId);

    /**
     * 根据角色id删除关联表
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

}