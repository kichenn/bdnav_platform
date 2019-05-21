package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.persistence.SchoolModeMapper;
import com.bdxh.school.service.SchoolModeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author admin
 */
@Service
@Slf4j
public class SchoolModeServiceImpl extends BaseService<SchoolMode> implements SchoolModeService {

    @Autowired
    private SchoolModeMapper schoolModeMapper;

    //查询列表信息根据条件
    @Override
    public List<SchoolMode> findSchoolModeInCondition(SchoolMode schoolMode){
        List<SchoolMode> results=super.select(schoolMode);
        return results;
    }

    //查询所有数量
    @Override
    public Integer getSchoolModeAllCount(){
        return schoolModeMapper.getSchoolModeAllCount();
    }

    //新增方法
    @Override
    public Boolean addSchoolMode(SchoolMode schoolMode){
        return schoolModeMapper.insertSelective(schoolMode) > 0;
    }

    //修改方法
    @Override
    public Boolean modifySchoolMode(SchoolMode schoolMode) {
        return schoolModeMapper.updateByPrimaryKey(schoolMode) > 0;
    }

    //删除方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delSchoolModeById(Long id){
        return schoolModeMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolModeInIds(List<Long> ids){
        return schoolModeMapper.delSchoolModeInIds(ids) > 0;
    }

    //根据ID查询对象的方法
    @Override
    public SchoolMode findSchoolModeById(Long id){
        return schoolModeMapper.selectByPrimaryKey(id);
    }

    @Override
    public SchoolMode getSchoolModesByName(String modelName,Long schoolId) {
        SchoolMode schoolMode=schoolModeMapper.getSchoolModesByName(modelName,schoolId);
        return schoolMode;
    }


    @Override
    public PageInfo<QuerySchoolMode> findListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<QuerySchoolMode> schoolModeLogs = schoolModeMapper.getByCondition(param);
        return new PageInfo(schoolModeLogs);
    }

    @Override
    public List<SchoolMode> getListByPlatform(Long schoolId,String platform) {
        return schoolModeMapper.getListByPlatform(schoolId,platform);
    }


}
