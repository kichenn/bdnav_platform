package com.bdxh.school.service.impl;

import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.service.SinglePermissionService;
import com.bdxh.school.vo.SinglePermissionShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.persistence.SinglePermissionMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-03-27 12:26:55
 */
@Service
@Slf4j
public class SinglePermissionServiceImpl extends BaseService<SinglePermission> implements SinglePermissionService {

    @Autowired
    private SinglePermissionMapper singlePermissionMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getSinglePermissionAllCount() {
        return singlePermissionMapper.getSinglePermissionAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSinglePermissionInIds(List<Long> ids) {
        return singlePermissionMapper.delSinglePermissionInIds(ids) > 0;
    }

    /**
     * 门禁单人信息根据条件分页查询
     *
     * @param singlePermissionQueryDto
     * @return
     */
    @Override
    public PageInfo<SinglePermissionShowVo> findSinglePermissionInConditionPage(SinglePermissionQueryDto singlePermissionQueryDto) {
        Page page = PageHelper.startPage(singlePermissionQueryDto.getPageNum(), singlePermissionQueryDto.getPageSize());
        SinglePermission singlePermission = new SinglePermission();
        BeanUtils.copyProperties(singlePermissionQueryDto, singlePermission);
        //设置类型
        if (singlePermissionQueryDto.getSingleUserTypeEnum()!=null){
            singlePermission.setUserType(singlePermissionQueryDto.getSingleUserTypeEnum().getKey());
        }
        PageInfo<SinglePermissionShowVo> pageInfo = new PageInfo(singlePermissionMapper.findSinglePermissionInConditionPage(singlePermission));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

}
