package com.bdxh.school.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolFence;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Repository
public interface SchoolFenceMapper extends Mapper<SchoolFence> {

    /**
     * 查询总条数
     */
    Integer getSchoolFenceAllCount();

    /**
     * 批量删除方法
     */
    Integer delSchoolFenceInIds(@Param("ids") List<Long> ids);

    List<SchoolFence> findFenceInConditionPaging(@Param("schoolFence") SchoolFence schoolFence);
}
