package com.bdxh.school.service.impl;

import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.service.SchoolFenceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.persistence.SchoolFenceMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Service
@Slf4j
public class SchoolFenceServiceImpl extends BaseService<SchoolFence> implements SchoolFenceService {

    @Autowired
    private SchoolFenceMapper schoolFenceMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getSchoolFenceAllCount() {
        return schoolFenceMapper.getSchoolFenceAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolFenceInIds(List<Long> ids) {
        return schoolFenceMapper.delSchoolFenceInIds(ids) > 0;
    }

    /**
     * 围栏分页条件查询
     *
     * @param schoolFenceQueryDto
     * @return
     */
    @Override
    public PageInfo<SchoolFence> findFenceInConditionPaging(SchoolFenceQueryDto schoolFenceQueryDto) {

        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(schoolFenceQueryDto, schoolFence);
        //设置状态值
        if (schoolFenceQueryDto.getGroupTypeEnum() != null) {
            schoolFence.setGroupType(schoolFenceQueryDto.getGroupTypeEnum().getKey());
        }
        if (schoolFenceQueryDto.getBlackStatusEnum() != null) {
            schoolFence.setStatus(schoolFenceQueryDto.getBlackStatusEnum().getKey());
        }

        Page page = PageHelper.startPage(schoolFenceQueryDto.getPageNum(), schoolFenceQueryDto.getPageSize());
        List<SchoolFence> schoolFences = schoolFenceMapper.findFenceInConditionPaging(schoolFence);

        PageInfo pageInfo = new PageInfo<>(schoolFences);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
