package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.persistence.SchoolStrategyMapper;
import com.bdxh.school.service.SchoolStrategyService;
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
public class SchoolStrategyServiceImpl extends BaseService<SchoolStrategy> implements SchoolStrategyService {

    @Autowired
    private SchoolStrategyMapper schoolStrategyMapper;

    //查询列表信息根据条件
    @Override
    public List<SchoolStrategy> findSchoolStrategyInCondition(SchoolStrategy schoolStrategy){
        List<SchoolStrategy> results=super.select(schoolStrategy);
        return results;
    }

    //查询所有数量
    @Override
    public Integer getSchoolStrategyAllCount(){
        return schoolStrategyMapper.getSchoolStrategyAllCount();
    }

    //新增方法
    @Override
    public Boolean addSchoolStrategy(SchoolStrategy schoolStrategy){
        return schoolStrategyMapper.insertSelective(schoolStrategy) > 0;
    }


    //修改方法
    @Override
    public Boolean modifySchoolStrategy(SchoolStrategy schoolStrategy) {
        return schoolStrategyMapper.updateByPrimaryKey(schoolStrategy) > 0;
    }


    //删除方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delSchoolStrategyById(Long id){
        return schoolStrategyMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolStrategyInIds(List<Long> ids){
        return schoolStrategyMapper.delSchoolStrategyInIds(ids) > 0;
    }

    //根据ID查询对象的方法
    @Override
    public SchoolStrategy findSchoolStrategyById(Long id){
        return schoolStrategyMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<QuerySchoolStrategy> findListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<QuerySchoolStrategy> schoolModeLogs = schoolStrategyMapper.getByCondition(param);
        return new PageInfo(schoolModeLogs);
    }

    @Override
    public SchoolStrategy getByPriority(String SchoolCode, Integer Priority) {
        return schoolStrategyMapper.getByPriority(SchoolCode,Priority);
    }

}
