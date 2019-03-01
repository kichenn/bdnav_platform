package com.bdxh.system.persistence;

import com.bdxh.system.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PermissionMapper extends Mapper<Permission> {

    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    List<String> getPermissionListByUserId(@Param("userId") Long userId);

}