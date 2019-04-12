package com.bdxh.system.persistence;

import com.bdxh.system.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper extends Mapper<Role> {
    /**
     * 根据条件查询角色
     * @param param
     * @return
     */
    List<Role> getByCondition(Map<String,Object> param);

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<String> getRoleListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色查询角色对象
     * @param role
     * @return
     */
    Role getRoleByRole(String role);

    /**
     * 查询全部角色
     */
    List<Role> getRoleList();

}