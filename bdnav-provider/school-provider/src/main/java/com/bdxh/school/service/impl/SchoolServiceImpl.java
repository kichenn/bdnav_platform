package com.bdxh.school.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.school.configration.anno.GetWithRedis;
import com.bdxh.school.configration.redis.RedisCache;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.persistence.SchoolMapper;
import com.bdxh.school.service.SchoolService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SchoolServiceImpl extends BaseService<School> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private RedisCache redisCache;

    //添加学校信息
    @Override
    public Boolean addSchool(SchoolDto schoolDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolDto, school);
        //school.setAppKey();
        //school.setAppSecret();
        Boolean result = schoolMapper.insertSelective(school) > 0;
//        SchoolVo schoolvo = new SchoolVo();
//        BeanUtils.copyProperties(school, schoolvo);
       /* if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
        }*/
        return result;
    }

    //修改学校信息
    @Override
    public Boolean modifySchool(ModifySchoolDto schoolDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolDto, school);
        Boolean result = schoolMapper.updateByPrimaryKey(school) > 0;
        /*if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.delete(SCHOOL_LIST_PREFIX + "_" + school.getId());
        }*/
        return result;
    }

    //删除学校信息
    @Override
    public Boolean delSchool(Long id) {
        Boolean result = schoolMapper.deleteByPrimaryKey(id) > 0;
        if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.delete(SCHOOL_LIST_PREFIX + "_" + id);
        }
        return result;
    }

    //批量删除学校信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchool(List<Long> id) {
        Boolean result = schoolMapper.batchDelSchool(id) > 0;
       /* if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
        }*/
        return result;
    }

    //id查询学校信息
    @Override
//    @GetWithRedis(key = SCHOOL_INFO_PREFIX)
    public Optional<School> findSchoolById(Long id) {
        School school = schoolMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(school);
    }

    //筛选条件查询学校信息(分页)
    @Override
//    @GetWithRedis(key = SCHOOL_LIST_PREFIX)
    public PageInfo<School> findSchoolsInCondition(SchoolQueryDto schoolQueryDto) {
        List<School> schools = schoolMapper.findIdsInCondition(schoolQueryDto.getSchooleCode(), schoolQueryDto.getSchooleName());
        return new PageInfo(schools);
    }

    //查询学校列表（全部，无条件）
    @Override
    public List<School> findSchools() {
        List<School> schools = schoolMapper.selectAll();
        return schools;
    }


}
