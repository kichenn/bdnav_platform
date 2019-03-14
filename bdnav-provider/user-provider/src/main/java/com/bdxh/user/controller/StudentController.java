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
import com.bdxh.common.utils.POIUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(value ="学生信息管理接口API", tags = "学生信息管理接口API")
@RestController
@RequestMapping("/student")
@Validated
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 新增学生信息
     * @param addStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="新增学生信息")
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody AddStudentDto addStudentDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Student student = BeanMapUtils.map(addStudentDto, Student.class);
            if (studentService.isNullStudent(student.getSchoolCode(),student.getCardNumber())==null){
                student.setId(snowflakeIdWorker.nextId());
                studentService.save(student);
                return WrapMapper.ok();
            }
            return WrapMapper.error("当前学校已有相同cardNumber(学号)");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除学生信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="删除学生信息")
    @RequestMapping(value = "/removeStudent",method = RequestMethod.POST)
    public Object removeStudent(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                               @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber){
        try{
            studentService.deleteStudentInfo(schoolCode,cardNumber);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除学生信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value="批量删除学生信息")
    @RequestMapping(value = "/removeStudents",method = RequestMethod.POST)
    public Object removeStudents(@RequestParam(name = "schoolCodes") @NotNull(message="学生学校Code不能为空")String schoolCodes,
                                @RequestParam(name = "cardNumbers") @NotNull(message="学生微校卡号不能为空")String cardNumbers){
        try{
            studentService.deleteBatchesStudentInfo(schoolCodes,cardNumbers);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改学生信息
     * @param updateStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="修改学生信息")
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public Object updateStudent(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
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
     * @param  schoolCode cardNumber
     * @return family
     */
    @ApiOperation(value="查询单个学生信息")
    @RequestMapping(value ="/queryStudentInfo",method = RequestMethod.GET)
    public Object queryStudentInfo(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber) {
        try {
           StudentVo studentVo= studentService.selectStudentVo(schoolCode,cardNumber);
           return  WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据条件分页查找
     * @param studentQueryDto
     * @return PageInfo<Family>
     */
    @ApiOperation(value="根据条件分页查询学生数据")
    @RequestMapping(value = "/queryStudentListPage",method = RequestMethod.POST)
    public Object queryStudentListPage(@RequestBody StudentQueryDto studentQueryDto) {
        try {
            // 封装分页之后的数据
            PageInfo<Student> student=studentService.getStudentList(studentQueryDto);
            return WrapMapper.ok(student);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
