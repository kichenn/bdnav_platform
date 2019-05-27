package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Student;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 学生信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:38
 **/
public interface StudentService extends IService<Student> {

    /**
     * 根据条件分页查询学生信息
     * @param studentQueryDto
     * @return
     */
    PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto);


    /**
     * 根据id删除学生信息以及学生家长绑定信息
     * @param schoolCode
     * @param cardNumber
     */
    void deleteStudentInfo(String schoolCode, String cardNumber);

    /**
     * 根据id批量删除学生信息以及学生家长绑定信息
     * @param schoolCodes
     * @param cardNumbers
     */
    void deleteBatchesStudentInfo(String schoolCodes, String cardNumbers);



    /**
     * 修改学生信息如果修改了名字则修改关系表学生名称
     * @param updateStudentDto
     */
    void updateStudentInfo(UpdateStudentDto updateStudentDto);

    /**
     * 查询单个学生详细信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    StudentVo selectStudentVo(String schoolCode, String cardNumber);


    /**
     * 判断数据库是否存在相同卡号
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    StudentVo isNullStudent(String schoolCode, String cardNumber);


    /**
     * @Description: 学校code，学校id，班级id查询学生信息
     * @Author: Kang
     * @Date: 2019/3/23 10:43
     */
    Student findStudentBySchoolClassId(String schoolCode, Long schoolId, Long classId);

    /**
     * 新增学生信息
     * @param student
     */
    void saveStudent(Student student);

    /**
     * 批量新增学生
     * @param studentList
     * @return
     */
    void batchSaveStudentInfo(List<AddStudentDto> studentList);

    /**
     * 根据学校Code查询所有学生学号
     * @param schoolCode
     * @return
     */
    List<String> queryCardNumberBySchoolCode(String schoolCode);

    /**
     * 根据学校CODE和组织架构查询学生
     * @param schoolCode
     * @param parentIds
     * @param type
     * @return
     */
    List<Student> findStudentInfoByClassOrg(String schoolCode,String parentIds,Byte type);


    /**
     * 学生信息批量修改
     */
    void studentBatchUpdate(List<Student> studentList);

    /**
     * 查询学生根据学号以及班级IDs和修改的院校是否父节点查找学生
     * @param schoolCode
     * @param classIds
     * @param type
     * @return
     */
    List<Student> findStudentInfoByClassId(String schoolCode,String classIds,String type);

    /**
     * 修改学校名字
     * @param schoolCode
     * @param schoolName
     */
    void updateSchoolName(String schoolCode,String schoolName);

    /**
     * 根据条件查询学生
     * @param studentQueryDto
     * @return
     */
    List<Student> findStudentInfo(StudentQueryDto studentQueryDto);



}
