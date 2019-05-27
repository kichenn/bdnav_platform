/**
 * Copyright (C), 2019-2019
 * FileName: StudentController
 * Author:   bdxh
 * Date:     2019/2/28 10:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.service.StudentService;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "学生信息管理接口API", tags = "学生信息管理接口API")
@RestController
@RequestMapping("/student")
@Validated
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;


    /**
     * 新增学生信息
     *
     * @param addStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增学生信息")
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody AddStudentDto addStudentDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Student student = BeanMapUtils.map(addStudentDto, Student.class);
            studentService.saveStudent(student);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除学生信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "删除学生信息")
    @RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    public Object removeStudent(@RequestParam(name = "schoolCode") @NotNull(message = "学生学校Code不能为空") String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message = "学生微校卡号不能为空") String cardNumber) {
        try {
            studentService.deleteStudentInfo(schoolCode, cardNumber);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除学生信息
     *
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value = "批量删除学生信息")
    @RequestMapping(value = "/removeStudents", method = RequestMethod.POST)
    public Object removeStudents(@RequestParam(name = "schoolCodes") @NotNull(message = "学生学校Code不能为空") String schoolCodes,
                                 @RequestParam(name = "cardNumbers") @NotNull(message = "学生微校卡号不能为空") String cardNumbers) {
        try {
            studentService.deleteBatchesStudentInfo(schoolCodes, cardNumbers);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改学生信息
     *
     * @param updateStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改学生信息")
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public Object updateStudent(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            studentService.updateStudentInfo(updateStudentDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个学生信息
     *
     * @param schoolCode cardNumber
     * @return family
     */
    @ApiOperation(value = "查询单个学生信息")
    @RequestMapping(value = "/queryStudentInfo", method = RequestMethod.GET)
    public Object queryStudentInfo(@RequestParam(name = "schoolCode") @NotNull(message = "学生学校Code不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "学生微校卡号不能为空") String cardNumber) {
        try {
            StudentVo studentVo = studentService.selectStudentVo(schoolCode, cardNumber);
            return WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     *
     * @param studentQueryDto
     * @return PageInfo<Family>
     */
    @ApiOperation(value = "根据条件分页查询学生数据")
    @RequestMapping(value = "/queryStudentListPage", method = RequestMethod.POST)
    public Object queryStudentListPage(@RequestBody StudentQueryDto studentQueryDto) {
        try {
            // 封装分页之后的数据
            PageInfo<Student> student = studentService.getStudentList(studentQueryDto);
            return WrapMapper.ok(student);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * @Description: 学校code，学校id，班级id查询学生信息
     * @Author: Kang
     * @Date: 2019/3/23 10:19
     */
    @ApiOperation(value = "学校code，学校id,班级id查询学生信息")
    @RequestMapping(value = "/findStudentBySchoolClassId", method = RequestMethod.GET)
    public Object findStudentBySchoolClassId(@RequestParam("schoolCode") String schoolCode, @RequestParam("schoolId") Long schoolId, @RequestParam("classId") Long classId) {
        return WrapMapper.ok(studentService.findStudentBySchoolClassId(schoolCode, schoolId, classId));
    }

    /**
     * 批量新增学生信息
     *
     * @param studentList
     * @return
     */
    @ApiOperation(value = "批量新增学生信息")
    @RequestMapping(value = "/batchSaveStudentInfo", method = RequestMethod.POST)
    public Object batchSaveStudentInfo(@RequestBody List<AddStudentDto> studentList) {
        try {
            studentService.batchSaveStudentInfo(studentList);
            return WrapMapper.ok();
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据学校Code查询所有学生学号
     * @param schoolCode
     * @return
     */
    @ApiOperation(value = "根据学校Code查询所有学生学号")
    @RequestMapping(value = "/queryCardNumberBySchoolCode", method = RequestMethod.POST)
    public Object queryCardNumberBySchoolCode(@RequestParam("schoolCode") String schoolCode) {
        try {
            return WrapMapper.ok(studentService.queryCardNumberBySchoolCode(schoolCode));
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据学校CODE和组织架构查询学生
     * @param schoolCode
     * @param parentIds
     * @param type
     * @return
     */
    @ApiOperation(value = "根据学校CODE和组织架构查询学生")
    @RequestMapping(value = "/findStudentInfoByClassOrg", method = RequestMethod.POST)
    public Object findStudentInfoByClassOrg(@RequestParam("schoolCode") String schoolCode,
                                            @RequestParam("parentIds") String parentIds,
                                            @RequestParam("type") Byte type) {
        try {
            return WrapMapper.ok(studentService.findStudentInfoByClassOrg(schoolCode, parentIds, type));
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询所有学生
     * @return
     */
    @ApiOperation(value = "查询所有学生")
    @RequestMapping(value = "/findAllStudent", method = RequestMethod.GET)
    public Object findAllStudent() {
        try {
            return WrapMapper.ok(studentService.selectAll());
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据条件查询学生
     * @param studentQueryDto
     * @return
     */
    @ApiOperation(value = "根据条件查询学生")
    @RequestMapping(value = "/findStudentInfo", method = RequestMethod.POST)
    public Object findStudentInfo(@RequestBody StudentQueryDto studentQueryDto){
        return WrapMapper.ok(studentService.findStudentInfo(studentQueryDto));
    }




}
