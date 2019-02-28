package com.bdxh.system.persistence;

import com.bdxh.system.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends Mapper<User> {
    /**
     * 根据条件查询字典
     * @param param
     * @return
     */
    List<User> getByCondition(Map<String,Object> param);
}