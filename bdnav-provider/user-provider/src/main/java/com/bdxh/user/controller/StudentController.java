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
import com.bdxh.user.configration.idgenerator.IdGeneratorProperties;
import com.bdxh.user.dto.StudentDto;
import com.bdxh.user.dto.StudentQueryDto;
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

@Api(value ="学生信息模块接口API", tags = "学生信息模块接口API")
@RestController
@RequestMapping("/student")
@Validated
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;


    @ApiOperation(value="新增学生信息")
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Student student = BeanMapUtils.map(studentDto, Student.class);
            IdGeneratorProperties idGeneratorProperties=new IdGeneratorProperties();
            student.setId(new SnowflakeIdWorker(idGeneratorProperties.getWorkerId(),idGeneratorProperties.getDatacenterId()).nextId());
           studentService.save(student);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value="根据ID删除学生信息")
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    public Object removeFamily(@RequestParam(name = "id") @NotNull(message = "学生id不能为空") String id){
        try{
            studentService.deleteStudentInfo(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value="根据ID批量删除学生信息")
    @RequestMapping(value = "/removeFamilys",method = RequestMethod.POST)
    public Object removeFamilys(@RequestParam(name = "id") @NotNull(message = "学生id不能为空") String id){
        try{
            String fid[]=id.split(",");
            studentService.deleteBatchesStudentInfo(fid);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    //修改学生信息
    @ApiOperation(value="修改学生信息")
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@Valid @RequestBody StudentDto studentDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Student student = BeanMapUtils.map(studentDto, Student.class);
            studentService.update(student);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改时根据Id查询
     * @param id
     * @return family
     */
    @ApiOperation(value="修改时根据Id查询单个学生信息")
    @RequestMapping(value ="/queryStudentInfo",method = RequestMethod.POST)
    public Object queryStudentInfo(@RequestParam(name = "id") @NotNull(message = "学生id不能为空")  Long id) {
        try {
               /* return studentService.selectStudentVo(id);*/
            return null;
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
