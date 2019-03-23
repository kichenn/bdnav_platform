/**
 * Copyright (C), 2019-2019
 * FileName: StudentControllerClient
 * Author:   binzh
 * Date:     2019/3/11 14:19
 * Description: TOOO
 * History:
 */
package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.fallback.StudentControllerFallback;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
@FeignClient(value = "user-provider-cluster", fallback = StudentControllerFallback.class)
public interface StudentControllerClient {

    /**
     * 查询学生数据
     * @param studentQueryDto
     * @return
     */
    @RequestMapping(value = "/student/queryStudentListPage",method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryStudentListPage(@RequestBody StudentQueryDto studentQueryDto);

    /**
     * 批量删除学生信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @RequestMapping(value = "/student/removeStudents",method = RequestMethod.POST)
    @ResponseBody
    Wrapper  removeStudents(@RequestParam("schoolCodes")String schoolCodes,@RequestParam("cardNumbers")String cardNumbers);

    /**
     * 修改学生信息
     * @param updateStudentDto
     * @return
     */
    @RequestMapping(value = "/student/updateStudent",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateStudent(@RequestBody UpdateStudentDto updateStudentDto);

    /**
     * 查询单个学生信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/student/queryStudentInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<StudentVo> queryStudentInfo(@RequestParam(name = "schoolCode")String schoolCode, @RequestParam(name = "cardNumber")String cardNumber);

    /**
     * 新增学生
     * @param addStudentDto
     * @return
     */
    @RequestMapping(value = "/student/addStudent",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addStudent(@RequestBody AddStudentDto addStudentDto);

    /**
     * 删除学生
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/student/removeStudent",method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeStudent(@RequestParam(name = "schoolCode")String schoolCode,@RequestParam(name = "cardNumber")String cardNumber);


    /**
     * @Description: 学校code，学校id，班级id查询学生信息
     * @Author: Kang
     * @Date: 2019/3/23 10:19
     */
    @RequestMapping(value = "/student/findStudentBySchoolClassId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<Student> findStudentBySchoolClassId(@RequestParam("schoolCode") String schoolCode, @RequestParam("schoolId") Long schoolId, @RequestParam("classId") Long classId);
}
