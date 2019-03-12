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

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
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

@Api(value ="老师信息管理接口API",tags = "老师信息管理接口API")
@RestController
@RequestMapping("/teacher")
@Validated
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @ApiOperation(value="新增老师信息")
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public Object addTeacher(@Valid @RequestBody AddTeacherDto addTeacherDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            addTeacherDto.setId(snowflakeIdWorker.nextId());
            teacherService.saveTeacherDeptInfo(addTeacherDto);
            return WrapMapper.ok();
        }catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value="根据ID删除老师信息")
    @RequestMapping(value = "/removeTeacher",method = RequestMethod.POST)
    public Object removeTeacher(@RequestParam(name = "schoolCode") @NotNull(message="老师学校Code不能为空")String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber){
        try{
            teacherService.deleteTeacherInfo(schoolCode, cardNumber);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value="根据ID批量删除老师信息")
    @RequestMapping(value = "/removeTeachers",method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "schoolCode") @NotNull(message="老师学校Code不能为空")String schoolCodes,
                                 @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumbers){
        try{
            teacherService.deleteBatchesTeacherInfo(schoolCodes, cardNumbers);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    //修改老师信息
    @ApiOperation(value="修改老师信息")
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public Object updateTeacher(@Valid @RequestBody UpdateTeacherDto updateTeacherDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            teacherService.updateTeacherInfo(updateTeacherDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改时根据Id查询
     * @param schoolCode cardNumber
     * @return
     */
    @ApiOperation(value="修改时根据Id查询单个老师信息")
    @RequestMapping(value ="/queryTeacherInfo",method = RequestMethod.POST)
    public Object queryTeacherInfo(@RequestParam(name = "schoolCode") @NotNull(message="老师学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber) {
        try {
            return teacherService.selectTeacherInfo(schoolCode,cardNumber);
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
