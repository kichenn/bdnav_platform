package com.bdxh.school.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.school.configration.anno.GetWithRedis;
import com.bdxh.school.configration.bean.PageVo;
import com.bdxh.school.configration.redis.RedisCache;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.persistence.SchoolMapper;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.vo.SchoolVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @description: 学校信息service实现
 * @author: xuyuan
 * @create: 2019-02-25 11:00
 **/
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
//        school.setId(snowflakeIdWorker.nextId());
        //school.setAppKey();
        //school.setAppSecret();
        Boolean result = schoolMapper.insertSelective(school) > 0;
//        SchoolVo schoolvo = new SchoolVo();
//        BeanUtils.copyProperties(school, schoolvo);
        if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
        }
        return result;
    }

    //修改学校信息
    @Override
    public Boolean modifySchool(ModifySchoolDto schoolDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolDto, school);
        Boolean result = schoolMapper.updateByPrimaryKey(school) > 0;
        if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.delete(SCHOOL_LIST_PREFIX + "_" + school.getId());
        }
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

    //id查询学校信息
    @Override
    @GetWithRedis(key = SCHOOL_INFO_PREFIX)
    public Optional<School> findSchoolById(Long id) {
        School school = schoolMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(school);
    }

    //筛选条件查询学校id
    @Override
    @GetWithRedis(key = SCHOOL_LIST_PREFIX)
    public Optional<List<String>> findSchoolIds(String schoolCode, String schoolName, Integer page, Integer limit) {
        Page<String> result = PageHelper.startPage(page + 1, limit).setOrderBy(" Id DESC").doSelectPage(() -> {
            schoolMapper.findIdsInCondition(schoolCode, schoolName);
        });
        return Optional.ofNullable(new PageVo<String>().init(result).getList());
    }

    //ids查询学校列表
    @Override
    public Optional<List<School>> findSchoolsByIds(List<String> ids) {
        return Optional.ofNullable(schoolMapper.findInfoInIds(ids));
    }


}
