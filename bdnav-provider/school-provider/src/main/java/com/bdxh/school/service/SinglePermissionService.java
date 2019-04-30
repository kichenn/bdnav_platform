package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.vo.SinglePermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-03-27 12:21:12
 */
@Service
public interface SinglePermissionService extends IService<SinglePermission> {

    /**
     * 查询所有数量
     */
    Integer getSinglePermissionAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelSinglePermissionInIds(List<Long> id);


    /**
     * 门禁单人信息根据条件分页查询
     *
     * @param singlePermissionQueryDto
     * @return
     */
    PageInfo<SinglePermissionShowVo> findSinglePermissionInConditionPage(SinglePermissionQueryDto singlePermissionQueryDto);
}
