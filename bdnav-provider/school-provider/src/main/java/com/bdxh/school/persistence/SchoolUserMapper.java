package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface SchoolUserMapper extends Mapper<SchoolUser> {

    /**
     * 根据条件查询字典
     * @param param
     * @return
     */
    List<SchoolUser> getByCondition(Map<String, Object> param);


    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    SchoolUser getByUserName(@Param("userName") String userName);

}