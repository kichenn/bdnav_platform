package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description: 学生信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:38
 **/
public interface StudentService extends IService<Student> {
    /**
     * @Author： binzh
     * @Description： 根据条件分页查询学生信息
     * @Date： 16:15 2019/3/4
     * @Param： [StudentQueryDto]
     * @return： PageInfo<Student>
     **/
    PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto);
    /**
     * @Author： binzh
     * @Description： //根据id删除学生信息以及学生家长绑定信息
     * @Date： 19:20 2019/3/7
     * @Param： [schoolCode, cardNumber,studentId]
     * @return： void
     **/
    void deleteStudentInfo(String schoolCode,String cardNumber);

  /**
   * @Author： binzh
   * @Description： //根据id批量删除学生信息以及学生家长绑定信息
   * @Date： 16:15 2019/3/7
   * @Param： [schoolCodes, cardNumbers, ids]
   * @return： void
   **/
    void deleteBatchesStudentInfo(String schoolCodes,String cardNumbers);

/**
 * @Author： binzh
 * @Description： //修改学生信息如果修改了名字则修改关系表学生名称
 * @Date： 9:41 2019/3/6
 * @Param： [studentDto]
 * @return： void
 **/
   void updateStudentInfo(UpdateStudentDto updateStudentDto);

   /**
    * @Author： binzh
    * @Description： //查询单个学生详细信息
    * @Date： 17:44 2019/3/8
    * @Param： [schoolCode, cardNumber]
    * @return： com.bdxh.user.vo.StudentVo
    **/
  StudentVo  selectStudentVo(String schoolCode,String cardNumber);

  /**
   * @Author： binzh
   * @Description： //判断数据库是否存在相同卡号
   * @Date： 17:45 2019/3/8
   * @Param： [schoolCode, cardNumber]
   * @return： com.bdxh.user.vo.StudentVo
   **/
  StudentVo isNullStudent(String schoolCode,String cardNumber);


}
