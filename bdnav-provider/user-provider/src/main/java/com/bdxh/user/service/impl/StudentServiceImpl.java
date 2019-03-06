package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.entity.Student;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.persistence.StudentMapper;
import com.bdxh.user.service.StudentService;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 学生信息service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:38
 **/
@Service
@Slf4j
public class StudentServiceImpl extends BaseService<Student> implements StudentService {

    @Autowired
    private FamilyStudentMapper familyStudentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto) {
        Student student = BeanMapUtils.map(studentQueryDto, Student.class);
        PageHelper.startPage(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        List<Student> listStudent = studentMapper.select(student);
        PageInfo<Student> pageInfoStudent = new PageInfo<Student>(listStudent);
        return pageInfoStudent;
    }


    @Override
    @Transactional
    public void deleteStudentInfo(String id) {
        studentMapper.deleteByPrimaryKey(Long.parseLong(id));
        FamilyStudent familyStudent=new FamilyStudent();
        familyStudent.setFamilyId(Long.parseLong(id));
        familyStudentMapper.delete(familyStudent);
    }


    @Override
    @Transactional
    public void deleteBatchesStudentInfo(String[] id) {
        for (int i=0; i<id.length; i++){
            studentMapper.deleteByPrimaryKey(Long.parseLong(id[i]));
            FamilyStudent familyStudent=new FamilyStudent();
            familyStudent.setFamilyId(Long.parseLong(id[i]));
            familyStudentMapper.delete(familyStudent);
        }
    }

   /* @Override
    public StudentVo selectStudentVo(Long id) {
        Student student=studentMapper.selectByPrimaryKey(id);
        return studentMapper.selectStudentVo(student.getSchoolCode(),student.getCardNumber());
    }*/
}
