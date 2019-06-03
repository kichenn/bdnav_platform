package com.bdxh.school.service.impl;


import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.school.dto.SchoolOrgAddDto;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.service.SchoolOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.persistence.SchoolOrgMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-05-31 14:06:08
 */
@Service
@Slf4j
public class SchoolOrgServiceImpl extends BaseService<SchoolOrg> implements SchoolOrgService {

    @Autowired
    private SchoolOrgMapper schoolOrgMapper;

    @Override
    public Integer getSchoolOrgAllCount() {
        return schoolOrgMapper.getSchoolOrgAllCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolOrgInIds(List<Long> ids) {
        return schoolOrgMapper.delSchoolOrgInIds(ids) > 0;
    }

    @Override
    public List<SchoolOrg> findAllSchoolOrgInfo(SchoolOrgQueryDto schoolOrgQueryDto) {
        Map<String, Object> param = BeanToMapUtil.objectToMap(schoolOrgQueryDto);
        return schoolOrgMapper.findAllSchoolOrgInfo(param);
    }

    @Override
    public SchoolOrg findSchoolOrgInfo(Long id) {
        return schoolOrgMapper.findSchoolOrgInfo(id);
    }

    @Override
    public List<SchoolOrg> findClassOrg(Long schoolId) {
        return schoolOrgMapper.findClassOrg(schoolId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeSchoolOrgInfo(Long id) {
        Boolean result = schoolOrgMapper.removeSchoolOrgInfo(id) > 0;
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSchoolOrgInfo(SchoolOrgUpdateDto schoolOrgUpdateDto) {
        Map<String, Object> param = BeanToMapUtil.objectToMap(schoolOrgUpdateDto);
        Boolean result = schoolOrgMapper.updateSchoolOrgInfo(param) > 0;
        return result;
    }

    @Override
    public List<SchoolOrg> findAllOrgInfo() {
        return  schoolOrgMapper.selectAll();
    }

    @Override
    public Boolean insertSchoolOrgInfo(SchoolOrgAddDto schoolOrgAddDto) {
        SchoolOrg schoolOrg=BeanMapUtils.map(schoolOrgAddDto,SchoolOrg.class);
        Boolean result= schoolOrgMapper.insert(schoolOrg)>0;
        return result;
    }
}
