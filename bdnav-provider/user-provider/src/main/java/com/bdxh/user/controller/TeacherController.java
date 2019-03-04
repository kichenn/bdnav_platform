/**
 * Copyright (C), 2019-2019
 * FileName: TeacherController
 * Author:   bdxh
 * Date:     2019/2/28 10:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.TeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.service.TeacherService;
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

@Api(value ="老师信息模块接口API",tags = "老师信息模块接口API")
@RestController
@RequestMapping("/teacher")
@Validated
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @ApiOperation(value="新增老师信息")
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public Object addTeacher(@Valid @RequestBody TeacherDto teacherDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
            Boolean result = teacherService.save(teacher) > 0 ;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value="根据ID删除老师信息")
    @RequestMapping(value = "/removeTeacher",method = RequestMethod.POST)
    public Object removeTeacher(@RequestParam(name = "id") @NotNull(message = "老师id不能为空") String id){
        try{
            teacherService.deleteTeacherInfo(id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value="根据ID批量删除老师信息")
    @RequestMapping(value = "/removeTeachers",method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "id") @NotNull(message = "老师id不能为空") String id){
        try{
            String fid[]=id.split(",");
            teacherService.deleteBatchesTeacherInfo(fid);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    //修改老师信息
    @ApiOperation(value="修改老师信息")
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public Object updateTeacher(@Valid @RequestBody TeacherDto teacherDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
            teacherService.update(teacher);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改时根据Id查询
     * @param id
     * @return
     */
    @ApiOperation(value="修改时根据Id查询单个老师信息")
    @RequestMapping(value ="/queryTeacherInfo",method = RequestMethod.POST)
    public Object queryTeacherInfo(@RequestParam(name = "id") @NotNull(message = "老师id不能为空")  Long id) {
        try {
            return teacherService.selectByKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据条件分页查找
     * @param teacherQueryDto
     * @return PageInfo<Family>
     */
    @ApiOperation(value="根据条件分页查询老师数据")
    @RequestMapping(value = "/queryTeacherListPage",method = RequestMethod.GET)
    public Object queryTeacherListPage(@ModelAttribute TeacherQueryDto teacherQueryDto) {
        try {
            // 封装分页之后的数据
            return teacherService.getTeacherList(teacherQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
