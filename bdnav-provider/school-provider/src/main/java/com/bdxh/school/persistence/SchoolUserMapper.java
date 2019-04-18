package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.vo.SchoolUserShowVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface SchoolUserMapper extends Mapper<SchoolUser> {

    /**
     * 根据条件查询字典
     * @param schoolUser
     * @return
     */
    List<SchoolUserShowVo> getByCondition(@Param("schoolUser") SchoolUser schoolUser, @Param("deptName") String deptName);


    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    SchoolUser getByUserName(@Param("userName") String userName);

    /** 用户id批量删除用户信息
     * @param ids
     * @return
     */
    Integer delBatchSchoolUserInIds(@Param("ids") List<Long> ids);
}