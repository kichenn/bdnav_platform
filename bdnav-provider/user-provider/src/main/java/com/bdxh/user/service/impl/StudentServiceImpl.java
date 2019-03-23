package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.persistence.StudentMapper;
import com.bdxh.user.service.StudentService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private FamilyMapper familyMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto) {
        PageHelper.startPage(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        List<Student> listStudent = studentMapper.selectAllStudentInfo(studentQueryDto);
        PageInfo<Student> pageInfoStudent = new PageInfo<Student>(listStudent);
        return pageInfoStudent;
    }

    @Override
    @Transactional
    public void deleteStudentInfo(String schoolCode, String cardNumber) {
        studentMapper.removeStudentInfo(schoolCode, cardNumber);
        //获取学生家长信息
        familyStudentMapper.studentRemoveFamilyStudentInfo(schoolCode, cardNumber);
    }

    @Override
    @Transactional
    public void deleteBatchesStudentInfo(String schoolCode, String cardNumber) {
        String[] schoolCodes = schoolCode.split(",");
        String[] cardNumbers = cardNumber.split(",");
        if (schoolCodes.length == cardNumbers.length) {
            for (int i = 0; i < cardNumbers.length; i++) {
                studentMapper.removeStudentInfo(schoolCodes[i], cardNumbers[i]);
                familyStudentMapper.studentRemoveFamilyStudentInfo(schoolCodes[i], cardNumbers[i]);
            }
        }
    }


    @Override
    @Transactional
    public void updateStudentInfo(UpdateStudentDto updateStudentDto) {
        studentMapper.updateStudentInfo(updateStudentDto);
        FamilyStudentVo familyStudentVo = familyStudentMapper.studentQueryInfo(
                updateStudentDto.getSchoolCode(),
                updateStudentDto.getCardNumber());
        if (null != familyStudentVo && !("").equals(familyStudentVo)) {
            if (!updateStudentDto.getName().equals(familyStudentVo.getSName())) {
                //修改关系表数据
                AddFamilyStudentDto familyStudentDto = new AddFamilyStudentDto();
                familyStudentDto.setStudentName(updateStudentDto.getName());
                familyStudentDto.setCardNumber(familyStudentVo.getFCardNumber());
                familyStudentDto.setSchoolCode(updateStudentDto.getSchoolCode());
                familyStudentMapper.updateStudentInfo(familyStudentDto);
            }
        }
    }

    @Override
    public StudentVo selectStudentVo(String schoolCode, String cardNumber) {
        StudentVo studentVo = studentMapper.selectStudentVo(schoolCode, cardNumber);
        FamilyStudentVo familyStudentVo = familyStudentMapper.studentQueryInfo(schoolCode, cardNumber);
        if (null != familyStudentVo) {
            String fNumber = familyStudentVo.getFCardNumber();
            if (null != fNumber && !("").equals(fNumber)) {
                FamilyVo familyVo = familyMapper.selectByCodeAndCard(schoolCode, fNumber);
                studentVo.setFName(familyVo.getName());
                studentVo.setCardNumber(fNumber);
                studentVo.setFPhone(familyVo.getPhone());
            }
        }
        return studentVo;
    }

    @Override
    public StudentVo isNullStudent(String schoolCode, String cardNumber) {
        return studentMapper.selectStudentVo(schoolCode, cardNumber);
    }

    /**
     * @Description: 学校code，学校id，班级id查询学生信息
     * @Author: Kang
     * @Date: 2019/3/23 10:43
     */
    @Override
    public Student findStudentBySchoolClassId(String schoolCode, Long schoolId, Long classId) {
        return studentMapper.findStudentBySchoolClassId(schoolCode,schoolId,classId);
    }

}
