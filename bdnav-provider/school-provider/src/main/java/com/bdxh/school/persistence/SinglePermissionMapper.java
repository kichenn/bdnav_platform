package com.bdxh.school.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.school.vo.SinglePermissionShowVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SinglePermission;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-03-27 12:09:22
 */
@Repository
public interface SinglePermissionMapper extends Mapper<SinglePermission> {
    /**
     * 查询总条数
     */
    Integer getSinglePermissionAllCount();

    /**
     * 批量删除方法
     */
    Integer delSinglePermissionInIds(@Param("ids") List<Long> ids);

    /**
     * 门禁单人信息根据条件分页查询
     */
    List<SinglePermissionShowVo> findSinglePermissionInConditionPage(@Param("singlePermission") SinglePermission singlePermission);
}
