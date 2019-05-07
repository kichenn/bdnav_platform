package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.vo.SchoolRoleShowVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface SchoolRoleMapper extends Mapper<SchoolRole> {
    /**
     * 根据条件查询角色
     *
     * @param schoolRole
     * @return
     */
    List<SchoolRoleShowVo> getByCondition(@Param("schoolRole") SchoolRole schoolRole);

    /**
     * 根据用户id查询角色列表(角色名称)
     *
     * @param userId
     * @return
     */
    List<String> getRoleListByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id查询角色列表(角色id)
     * @param userId
     * @return
     */
    List<Long> getRoleIdListByUserId(@Param("userId") Long userId);

    /**
     * 用户id查询角色id和角色名称
     *
     * @param userId
     * @return
     */
    List<Map<Long, String>> findRoleByUserIdResultMap(@Param("userId") Long userId);

    /**
     * 根据角色查询角色对象
     *
     * @param role
     * @return
     */
    SchoolRole getRoleByRole(String role);

    /**
     * 批量删除角色，根据角色id
     *
     * @param ids
     * @return
     */
    Integer delBatchRoleByRoleIds(@Param("ids") List<Long> ids);
}