package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.user.dto.TeacherDeptDto;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.TeacherDeptMapper;
import com.bdxh.user.persistence.TeacherMapper;
import com.bdxh.user.service.TeacherService;
import com.bdxh.user.vo.TeacherDeptVo;
import com.bdxh.user.vo.TeacherVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

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
    public void deleteTeacherInfo(String schoolCode,String cardNumber) {
        teacherMapper.deleteTeacher(schoolCode, cardNumber);
        teacherDeptMapper.deleteTeacherDept(schoolCode, cardNumber);
    }


    @Override
    @Transactional
    public void deleteBatchesTeacherInfo(String schoolCodes,String cardNumbers) {
        String[] schoolCode=schoolCodes.split(",");
        String[] cardNumber=cardNumbers.split(",");
        if(schoolCode.length==cardNumber.length){
            for (int i=0; i<cardNumber.length; i++){
                teacherMapper.deleteTeacher(schoolCode[i],cardNumber[i]);
                teacherDeptMapper.deleteTeacherDept(schoolCode[i],cardNumber[i]);
            }
        }

    }

    @Override
    @Transactional
    public void saveTeacherDeptInfo(AddTeacherDto teacherDto) {
        Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
        teacherMapper.insert(teacher);
        IntStream.range(0,teacherDto.getTeacherDeptDtoList().size())
                .forEach(i -> {
                    TeacherDept teacherDept=new TeacherDept();
                    teacherDept.setId(snowflakeIdWorker.nextId());
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

    @Override
    public TeacherVo selectTeacherInfo(String schoolCode, String cardNumber) {
        TeacherVo teacherVo=teacherMapper.selectTeacherDetails(schoolCode, cardNumber);
        List<TeacherDeptVo> teacherDeptVo=teacherDeptMapper.selectTeacherDeptDetailsInfo(schoolCode, cardNumber);
        teacherVo.setTeacherDeptVos(teacherDeptVo);
        return teacherVo;
    }

    @Override
    public void updateTeacherInfo(UpdateTeacherDto updateTeacherDto) {
        teacherMapper.updateTeacher(updateTeacherDto);
            teacherDeptMapper.deleteTeacherDept(updateTeacherDto.getSchoolCode(),updateTeacherDto.getCardNumber());
        for (int i=0;i<updateTeacherDto.getTeacherDeptDtoList().size();i++){
            TeacherDeptDto teacherDeptDto=new TeacherDeptDto();
            teacherDeptDto.setId(snowflakeIdWorker.nextId());
            teacherDeptDto.setSchoolCode(updateTeacherDto.getSchoolCode());
            teacherDeptDto.setCardNumber(updateTeacherDto.getCardNumber());
            teacherDeptDto.setTeacherId(updateTeacherDto.getId());
            teacherDeptDto.setDeptId(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptId());
            teacherDeptDto.setDeptName(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptName());
            teacherDeptDto.setDeptIds(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
            teacherDeptDto.setDeptNames(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames());
            TeacherDept teacherDept = BeanMapUtils.map(teacherDeptDto, TeacherDept.class);
            teacherDeptMapper.insert(teacherDept);
        }
    }
}
