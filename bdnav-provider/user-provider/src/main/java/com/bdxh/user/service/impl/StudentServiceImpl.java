package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.BaseUser;
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

    /**
     * 查询所有学生
     * @param studentQueryDto
     * @return
     */
    @Override
    public PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto) {
        PageHelper.startPage(studentQueryDto.getPageNum(), studentQueryDto.getPageSize());
        List<Student> listStudent = studentMapper.selectAllStudentInfo(studentQueryDto);
        PageInfo<Student> pageInfoStudent = new PageInfo<Student>(listStudent);
        return pageInfoStudent;
    }

    /**
     * 删除学生信息
     * @param schoolCode
     * @param cardNumber
     */
    @Override
    @Transactional
    public void deleteStudentInfo(String schoolCode, String cardNumber) {
        studentMapper.removeStudentInfo(schoolCode, cardNumber);
        familyStudentMapper.studentRemoveFamilyStudentInfo(schoolCode, cardNumber);
        baseUserMapper.deleteBaseUserInfo(schoolCode ,cardNumber );
    }

    /**
     * 批量删除学生信息
     * @param schoolCode
     * @param cardNumber
     */
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


    /**
     * 修改学生信息
     * @param updateStudentDto
     */
    @Override
    @Transactional
    public void updateStudentInfo(UpdateStudentDto updateStudentDto) {
        try {
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
        //修改时判断用户是否已经激活
        if(updateStudentDto.getActivate().equals(Byte.parseByte("2"))) {
            SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
            synUserInfoRequest.setSchool_code(/*updateStudentDto.getSchoolCode()*/"1044695883");
            synUserInfoRequest.setCard_number(updateStudentDto.getCardNumber());
            synUserInfoRequest.setName(updateStudentDto.getName());
            synUserInfoRequest.setGender(updateStudentDto.getGender() == 1 ? "男" : "女");
            if(updateStudentDto.getSchoolType()>=Byte.parseByte("4")){
                synUserInfoRequest.setClass_name(updateStudentDto.getClassName());
                synUserInfoRequest.setGrade(updateStudentDto.getGradeName());
                synUserInfoRequest.setCollege(updateStudentDto.getCollegeName());
                synUserInfoRequest.setProfession(updateStudentDto.getProfessionName());
            }else{
                synUserInfoRequest.setClass_name(updateStudentDto.getClassName());
                synUserInfoRequest.setGrade(updateStudentDto.getGradeName());
            }
            synUserInfoRequest.setOrganization(updateStudentDto.getClassNames());
            synUserInfoRequest.setTelephone(updateStudentDto.getPhone());
            synUserInfoRequest.setCard_type("1");
            synUserInfoRequest.setId_card(updateStudentDto.getIdcard());
            synUserInfoRequest.setHead_image(updateStudentDto.getImage());
            synUserInfoRequest.setIdentity_type(AuthenticationConstant.STUDENT);
            synUserInfoRequest.setNation(updateStudentDto.getNationName());
            synUserInfoRequest.setQq(updateStudentDto.getQqNumber());
            synUserInfoRequest.setAddress(updateStudentDto.getAdress());
            synUserInfoRequest.setEmail(updateStudentDto.getEmail());
            synUserInfoRequest.setPhysical_card_number(updateStudentDto.getPhysicalNumber());
            synUserInfoRequest.setPhysical_chip_number(updateStudentDto.getPhysicalChipNumber());
            synUserInfoRequest.setDorm_number(updateStudentDto.getDormitoryAddress());
            synUserInfoRequest.setCampus(updateStudentDto.getCampusName());
            String result= AuthenticationUtils.synUserInfo(synUserInfoRequest,updateStudentDto.getAppKey(),updateStudentDto.getAppSecret());
            JSONObject jsonObject= JSONObject.parseObject(result);
            if(!jsonObject.get("errcode").equals(0)){
                throw new Exception("学生信息同步失败,返回的错误码"+jsonObject.get("errcode")+"，同步学生卡号="+updateStudentDto.getCardNumber()+"学校名称="+updateStudentDto.getSchoolName());
            }
        }
    }catch (Exception e){
        e.printStackTrace();
        log.info("更新学生信息失败，错误信息="+e.getMessage());
    }
    }

    /**
     * 查询单个学生信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
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

    /**
     * 查询单个学生是否存在
     * @param schoolCode
     * @param cardNumber
     * @return
     */
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

    /**
     * 保存学生信息
     * @param student
     */
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

    /**
     * 批量保存学生信息
     * @param addStudentDtoList
     */
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

    /**
     * 根据学校CODE查询所有卡号
     * @param schoolCode
     * @return
     */
    @Override
    public List<String> queryCardNumberBySchoolCode(String schoolCode) {
        return studentMapper.queryCardNumberBySchoolCode(schoolCode);
    }

    /**
     * 根据组织架构信息查询学生
     * @param schoolCode
     * @param parentIds
     * @param type
     * @return
     */
    @Override
    public List<Student> findStudentInfoByClassOrg(String schoolCode, String parentIds, Byte type) {
        return studentMapper.findStudentInfoByClassOrg(schoolCode,parentIds,type);
    }
}
