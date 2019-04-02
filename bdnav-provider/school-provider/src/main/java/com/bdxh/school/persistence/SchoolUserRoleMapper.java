package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolUserRoleMapper extends Mapper<SchoolUserRole> {


    //根据id查找对象列表
    List<SchoolRole> findUserRole(Long RoleId);

    /**
     * 根据角色id删除关联表
     *
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户id查找用户角色关系
     *
     * @param userId
     * @return
     */
    List<SchoolUserRole> findUserRoleByUserId(Long userId);

    /**
     * 批量删除角色，根据角色id
     *
     * @param roleIds
     * @return
     */
    Integer delBatchRoleByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * @Description: 用户id删除相关角色
     * @Author: Kang
     * @Date: 2019/4/2 11:39
     */
    Integer delRoleByUserId(@Param("userId") Long userId);

}