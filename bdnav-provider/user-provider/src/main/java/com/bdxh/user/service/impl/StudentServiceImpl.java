package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.entity.Student;
import com.bdxh.user.persistence.BaseUserMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
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
    private BaseUserMapper baseUserMapper;

    @Autowired
    private FamilyStudentMapper familyStudentMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
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
        familyStudentMapper.studentRemoveFamilyStudentInfo(schoolCode, cardNumber);
        baseUserMapper.deleteBaseUserInfo(schoolCode ,cardNumber );
    }

    @Override
    @Transactional
    public void deleteBatchesStudentInfo(String schoolCode, String cardNumber) {
        String[] schoolCodes = schoolCode.split(",");
        String[] cardNumbers = cardNumber.split(",");

            List<Map<String,String>>list =new ArrayList<>();
            for (int i = 0; i < cardNumbers.length; i++) {
                Map<String,String> map=new HashMap<>();
                map.put("cardNumber",cardNumbers[i]);
                map.put("schoolCode",schoolCodes[i]);
                list.add(map);
            studentMapper.batchRemoveStudentInfo(list);
            familyStudentMapper.batchRemoveFamilyStudentInfo(list);
            baseUserMapper.batchRemoveBaseUserInfo(list);
        }
    }


    @Override
    @Transactional
    public void updateStudentInfo(UpdateStudentDto updateStudentDto) {
        Student student = BeanMapUtils.map(updateStudentDto, Student.class);
        studentMapper.updateStudentInfo(student);
        BaseUser updateBaseUserDto = BeanMapUtils.map(updateStudentDto, BaseUser.class);
        baseUserMapper.updateBaseUserInfo(updateBaseUserDto);
        FamilyStudentVo familyStudentVo = familyStudentMapper.studentQueryInfo(
                updateStudentDto.getSchoolCode(),
                updateStudentDto.getCardNumber());
        if (null != familyStudentVo && !("").equals(familyStudentVo)) {
            if (!updateStudentDto.getName().equals(familyStudentVo.getSName())) {
                //修改关系表数据
                FamilyStudent familyStudent = new FamilyStudent();
                familyStudent.setStudentName(updateStudentDto.getName());
                familyStudent.setCardNumber(familyStudentVo.getFCardNumber());
                familyStudent.setSchoolCode(updateStudentDto.getSchoolCode());
                familyStudentMapper.updateStudentInfo(familyStudent);

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

    @Override
    @Transactional
    public void saveStudent(Student student) {
        student.setId(snowflakeIdWorker.nextId());
        student.setActivate(Byte.valueOf("1"));
        studentMapper.insert(student);
        BaseUser baseUser = BeanMapUtils.map(student, BaseUser.class);
        baseUser.setUserType(1);
        baseUser.setUserId(student.getId());
        baseUser.setId(snowflakeIdWorker.nextId());
        baseUserMapper.insert(baseUser);
    }

    @Override
    public void batchSaveStudentInfo(List<AddStudentDto> addStudentDtoList) {
        List<Student> studentList=BeanMapUtils.mapList(addStudentDtoList,Student.class);
        List<BaseUser> baseUserList=BeanMapUtils.mapList(studentList,BaseUser.class);
        for (int i = 0; i<baseUserList.size(); i++) {
            studentList.get(i).setId(snowflakeIdWorker.nextId());
            baseUserList.get(i).setUserType(1);
            baseUserList.get(i).setUserId(studentList.get(i).getId());
            baseUserList.get(i).setId(snowflakeIdWorker.nextId());
        }
        studentMapper.batchSaveStudentInfo(studentList);
        baseUserMapper.batchSaveBaseUserInfo(baseUserList);

    }

    @Override
    public List<String> queryCardNumberBySchoolCode(String schoolCode) {
        return studentMapper.queryCardNumberBySchoolCode(schoolCode);
    }

    @Override
    public List<Student> findStudentInfoByClassOrg(String schoolCode, String parentIds, Byte type) {
        return studentMapper.findStudentInfoByClassOrg(schoolCode,parentIds,type);
    }
}
