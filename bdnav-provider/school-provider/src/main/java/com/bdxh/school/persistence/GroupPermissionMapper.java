package com.bdxh.school.persistence;

import java.security.acl.Group;
import java.util.List;

import com.bdxh.school.vo.GroupPermissionShowVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.GroupPermission;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-03-27 12:09:22
 */
@Repository
public interface GroupPermissionMapper extends Mapper<GroupPermission> {
    /**
     * 查询总条数
     */
    Integer getGroupPermissionAllCount();

    /**
     * 批量删除方法
     */
    Integer delGroupPermissionInIds(@Param("ids") List<Long> ids);

    /**
     * 部门id+部门类型查询学校组门禁
     */
    GroupPermission findGroupRecursionPermissionIds(@Param("groupId") Long groupId, @Param("groupType") Byte groupType);

    /**
     * 分页查询学校组门禁信息
     *
     * @param groupPermission
     * @return
     */
    List<GroupPermissionShowVo> findGroupPermissionInConditionPage(@Param("permission") GroupPermission groupPermission);
}
