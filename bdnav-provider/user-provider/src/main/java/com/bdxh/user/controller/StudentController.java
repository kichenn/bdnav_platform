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
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @ApiOperation(value="根据ID删除学生信息")
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    public Object removeFamily(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                               @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber){
        try{
            studentService.deleteStudentInfo(schoolCode,cardNumber);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value="根据ID批量删除学生信息")
    @RequestMapping(value = "/removeFamilys",method = RequestMethod.POST)
    public Object removeFamilys(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCodes,
                                @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumbers){
        try{
            studentService.deleteBatchesStudentInfo(schoolCodes,cardNumbers);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    //修改学生信息
    @ApiOperation(value="修改学生信息")
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult){
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
     * 修改时根据Id查询
     * @param  schoolCode cardNumber
     * @return family
     */
    @ApiOperation(value="修改时查询单个学生信息")
    @RequestMapping(value ="/queryStudentInfo",method = RequestMethod.POST)
    public Object queryStudentInfo(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber) {
        try {
               return studentService.selectStudentVo(schoolCode,cardNumber);
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
    @RequestMapping(value = "/queryStudentListPage",method = RequestMethod.GET)
    public Object queryFamilyListPage(@ModelAttribute StudentQueryDto studentQueryDto) {
        try {
            // 封装分页之后的数据
            return studentService.getStudentList(studentQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
