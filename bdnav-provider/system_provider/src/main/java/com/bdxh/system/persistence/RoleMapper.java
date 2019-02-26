package com.bdxh.system.persistence;

import com.bdxh.system.entity.Role;
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

}