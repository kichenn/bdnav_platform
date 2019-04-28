package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.FenceEntityDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.vo.SchoolFenceShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Service
public interface SchoolFenceService extends IService<SchoolFence> {

    /**
     * 查询所有数量
     */
    Integer getSchoolFenceAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelSchoolFenceInIds(List<Long> id);

    /**
     * 增加学校围栏
     *
     * @param schoolFence
     * @return
     */
    Boolean addFence(SchoolFence schoolFence, List<FenceEntityDto> fenceEntityDto) throws RuntimeException;


    /**
     * 修改学校围栏
     *
     * @param schoolFence
     * @return
     * @throws RuntimeException
     */
    Boolean modifyFence(SchoolFence schoolFence, List<FenceEntityDto> fenceEntityDto) throws RuntimeException;

    /**
     * 删除学校围栏
     *
     * @param id
     * @return
     */
    Boolean delFence(Long id) throws RuntimeException;

    /**
     * 围栏分页条件查询
     *
     * @param schoolFenceQueryDto
     * @return
     */
    PageInfo<SchoolFenceShowVo> findFenceInConditionPaging(SchoolFenceQueryDto schoolFenceQueryDto);

}
