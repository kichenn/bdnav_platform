package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.user.configration.idgenerator.IdGeneratorProperties;
import com.bdxh.user.dto.TeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.TeacherDeptMapper;
import com.bdxh.user.persistence.TeacherMapper;
import com.bdxh.user.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * @description: 老师信息service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:39
 **/
@Service
@Slf4j
public class TeacherServiceImpl extends BaseService<Teacher> implements TeacherService {
    @Autowired
    private TeacherDeptMapper teacherDeptMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public PageInfo<Teacher> getTeacherList(TeacherQueryDto teacherQueryDto) {
        Teacher teacher = BeanMapUtils.map(teacherQueryDto, Teacher.class);
        PageHelper.startPage(teacherQueryDto.getPageNum(), teacherQueryDto.getPageSize());
        List<Teacher> listTeacher = teacherMapper.select(teacher);
        PageInfo<Teacher> pageInfoTeacher = new PageInfo<Teacher>(listTeacher);
        return pageInfoTeacher;
    }


    @Override
    @Transactional
    public void deleteTeacherInfo(String id) {
        teacherMapper.deleteByPrimaryKey(Long.parseLong(id));
        TeacherDept teacherDept=new TeacherDept();
        teacherDept.setTeacherId(Long.parseLong(id));
        teacherDeptMapper.delete(teacherDept);
    }


    @Override
    @Transactional
    public void deleteBatchesTeacherInfo(String[] id) {
        for (int i=0; i<id.length; i++){
            teacherMapper.deleteByPrimaryKey(Long.parseLong(id.toString()));
            TeacherDept teacherDept=new TeacherDept();
            teacherDept.setTeacherId(Long.parseLong(id[i]));
            teacherDeptMapper.delete(teacherDept);
        }
    }

    @Override
    @Transactional
    public void saveTeacherDeptInfo(TeacherDto teacherDto) {
        Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
        teacherMapper.insert(teacher);
        IdGeneratorProperties idGeneratorProperties=new IdGeneratorProperties();
        IntStream.range(0,teacherDto.getTeacherDeptDtoList().size())
                .forEach(i -> {
                    TeacherDept teacherDept=new TeacherDept();
                    teacherDept.setId(new SnowflakeIdWorker(idGeneratorProperties.getWorkerId(),idGeneratorProperties.getDatacenterId()).nextId());
                    teacherDept.setSchoolCode(teacher.getSchoolCode());
                    teacherDept.setCardNumber(teacher.getCardNumber());
                    teacherDept.setTeacherId(teacher.getId());
                    teacherDept.setDeptId(teacherDto.getTeacherDeptDtoList().get(i).getDeptId());
                    teacherDept.setDeptName(teacherDto.getTeacherDeptDtoList().get(i).getDeptName());
                    teacherDept.setDeptIds(teacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
                    teacherDept.setDeptNames(teacherDto.getTeacherDeptDtoList().get(i).getDeptNames());
                    teacherDeptMapper.insert(teacherDept);
                        } );
    }
}
