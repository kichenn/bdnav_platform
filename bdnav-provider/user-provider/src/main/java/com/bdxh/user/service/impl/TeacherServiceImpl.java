package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.support.BaseService;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.BaseUserMapper;
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

    @Autowired
    private BaseUserMapper baseUserMapper;
    @Override
    public PageInfo<Teacher> getTeacherList(TeacherQueryDto teacherQueryDto) {
        PageHelper.startPage(teacherQueryDto.getPageNum(), teacherQueryDto.getPageSize());
        List<Teacher> listTeacher = teacherMapper.selectAllTeacherInfo(teacherQueryDto);
        PageInfo<Teacher> pageInfoTeacher = new PageInfo<Teacher>(listTeacher);
        return pageInfoTeacher;
    }


    @Override
    @Transactional
    public void deleteTeacherInfo(String schoolCode,String cardNumber) {
        teacherMapper.deleteTeacher(schoolCode, cardNumber);
        teacherDeptMapper.deleteTeacherDept(schoolCode, cardNumber);
        baseUserMapper.deleteBaseUserInfo(schoolCode,cardNumber);
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
                baseUserMapper.deleteBaseUserInfo(schoolCode[i],cardNumber[i]);
            }
        }
    }

    @Override
    @Transactional
    public void saveTeacherDeptInfo(AddTeacherDto teacherDto) {
        Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
        teacher.setId(snowflakeIdWorker.nextId());
        teacher.setActivate(Byte.valueOf("1"));
        teacherMapper.insert(teacher);
        BaseUser baseUser = BeanMapUtils.map(teacher, BaseUser.class);
        baseUser.setUserType(2);
        baseUser.setUserId(teacher.getId());
        baseUser.setId(snowflakeIdWorker.nextId());
        baseUserMapper.insert(baseUser);
        if(null!=teacherDto.getTeacherDeptDtoList()) {
            IntStream.range(0, teacherDto.getTeacherDeptDtoList().size())
                    .forEach(i -> {
                        TeacherDept teacherDept = new TeacherDept();
                        teacherDept.setId(snowflakeIdWorker.nextId());
                        teacherDept.setSchoolCode(teacher.getSchoolCode());
                        teacherDept.setCardNumber(teacher.getCardNumber());
                        teacherDept.setTeacherId(teacher.getId());
                        teacherDept.setDeptId(teacherDto.getTeacherDeptDtoList().get(i).getDeptId());
                        teacherDept.setDeptName(teacherDto.getTeacherDeptDtoList().get(i).getDeptName());
                        teacherDept.setDeptIds(teacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
                        teacherDept.setDeptNames(teacherDto.getTeacherDeptDtoList().get(i).getDeptNames());
                        teacherDeptMapper.insert(teacherDept);
                    });
        }
    }

    @Override
    public TeacherVo selectTeacherInfo(String schoolCode, String cardNumber) {
        Teacher teacher=teacherMapper.selectTeacherDetails(schoolCode, cardNumber);
        TeacherVo teacherVo = BeanMapUtils.map(teacher, TeacherVo.class);
        List<TeacherDeptVo> teacherDeptVo=teacherDeptMapper.selectTeacherDeptDetailsInfo(schoolCode, cardNumber);
        teacherVo.setTeacherDeptVos(teacherDeptVo);
        return teacherVo;
    }

    @Override
    @Transactional
    public void updateTeacherInfo(UpdateTeacherDto updateTeacherDto) {
        teacherMapper.updateTeacher(updateTeacherDto);
        UpdateBaseUserDto updateBaseUserDto = BeanMapUtils.map(updateTeacherDto, UpdateBaseUserDto.class);
        baseUserMapper.updateBaseUserInfo(updateBaseUserDto);
            teacherDeptMapper.deleteTeacherDept(updateTeacherDto.getSchoolCode(),updateTeacherDto.getCardNumber());
        for (int i=0;i<updateTeacherDto.getTeacherDeptDtoList().size();i++){
            TeacherDeptDto teacherDeptDto=new TeacherDeptDto();
            teacherDeptDto.setId(snowflakeIdWorker.nextId());
            teacherDeptDto.setSchoolCode(updateTeacherDto.getSchoolCode());
            teacherDeptDto.setCardNumber(updateTeacherDto.getCardNumber());
            teacherDeptDto.setTeacherId(updateTeacherDto.getId());
            String [] ids=updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds().split(",");
            String [] names=updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames().split("\\/");
            updateTeacherDto.getTeacherDeptDtoList().get(i).setDeptId(Long.parseLong(ids[ids.length-1]));
            updateTeacherDto.getTeacherDeptDtoList().get(i).setDeptName(names[names.length-1]);
            teacherDeptDto.setDeptIds(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
            teacherDeptDto.setDeptNames(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames());
            TeacherDept teacherDept = BeanMapUtils.map(teacherDeptDto, TeacherDept.class);
            teacherDeptMapper.insert(teacherDept);
        }
    }
}
