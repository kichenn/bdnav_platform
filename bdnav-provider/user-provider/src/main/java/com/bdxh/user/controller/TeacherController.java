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

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.service.TeacherDeptService;
import com.bdxh.user.service.TeacherService;
import com.bdxh.user.vo.TeacherVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "老师信息管理接口API", tags = "老师信息管理接口API")
@RestController
@RequestMapping("/teacher")
@Validated
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherDeptService teacherDeptService;



    /**
     * 新增老师信息
     *
     * @param addTeacherDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增老师信息")
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public Object addTeacher(@Valid @RequestBody AddTeacherDto addTeacherDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
         /*   TeacherVo teacherVo=teacherService.selectTeacherInfo(addTeacherDto.getSchoolCode(),addTeacherDto.getCardNumber());
            if(teacherVo!=null) {
              return WrapMapper.ok("当前学校已有相同cardNumber(学号)");
            }*/
            if (null != addTeacherDto.getTeacherDeptDtoList()) {
                for (int i = 0; i < addTeacherDto.getTeacherDeptDtoList().size(); i++) {
                    String[] ids = addTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds().split(",");
                    String[] names = addTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames().split("\\/");
                    addTeacherDto.getTeacherDeptDtoList().get(i).setDeptId(Long.parseLong(ids[ids.length - 1]));
                    addTeacherDto.getTeacherDeptDtoList().get(i).setDeptName(names[names.length - 1]);
                }
            }
            teacherService.saveTeacherDeptInfo(addTeacherDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除老师信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "删除老师信息")
    @RequestMapping(value = "/removeTeacher", method = RequestMethod.POST)
    public Object removeTeacher(@RequestParam(name = "schoolCode") @NotNull(message = "老师学校Code不能为空") String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message = "老师微校卡号不能为空") String cardNumber) {
        try {
            teacherService.deleteTeacherInfo(schoolCode, cardNumber);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除老师信息
     *
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value = "批量删除老师信息")
    @RequestMapping(value = "/removeTeachers", method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "schoolCodes") @NotNull(message = "老师学校Code不能为空") String schoolCodes,
                                 @RequestParam(name = "cardNumbers") @NotNull(message = "老师微校卡号不能为空") String cardNumbers) {
        try {
            teacherService.deleteBatchesTeacherInfo(schoolCodes, cardNumbers);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改老师信息
     *
     * @param updateTeacherDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改老师信息")
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public Object updateTeacher(@Valid @RequestBody UpdateTeacherDto updateTeacherDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
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
     * 查询老师信息
     *
     * @param schoolCode cardNumber
     * @return
     */
    @ApiOperation(value = "查询老师信息")
    @RequestMapping(value = "/queryTeacherInfo", method = RequestMethod.GET)
    public Object queryTeacherInfo(@RequestParam(name = "schoolCode") @NotNull(message = "老师学校Code不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "老师微校卡号不能为空") String cardNumber) {
        try {
            TeacherVo teacherVo = teacherService.selectTeacherInfo(schoolCode, cardNumber);
            return WrapMapper.ok(teacherVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     *
     * @param teacherQueryDto
     * @return PageInfo<Family>
     */
    @ApiOperation(value = "根据条件分页查询老师数据")
    @RequestMapping(value = "/queryTeacherListPage", method = RequestMethod.POST)
    public Object queryTeacherListPage(@RequestBody TeacherQueryDto teacherQueryDto) {
        try {
            // 封装分页之后的数据
            PageInfo<TeacherVo> teacher = teacherService.getTeacherList(teacherQueryDto);
            return WrapMapper.ok(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * @Description: 学校code，学校id，部门id查询老师信息
     * @Author: Kang
     * @Date: 2019/3/23 11:36
     */
    @ApiOperation(value = "学校code，学校id，部门id查询老师信息")
    @RequestMapping(value = "/findTeacherBySchoolDeptId", method = RequestMethod.GET)
    public Object findTeacherBySchoolDeptId(@RequestParam("schoolCode") String schoolCode, @RequestParam("schoolId") Long schoolId, @RequestParam("deptId") Long deptId) {
        return WrapMapper.ok(teacherDeptService.findTeacherBySchoolDeptId(schoolCode, schoolId, deptId));
    }
    @ApiOperation(value = "批量新增老师信息")
    @RequestMapping(value = "/batchSaveTeacherInfo", method = RequestMethod.POST)
    public Object batchSaveTeacherInfo(@RequestBody List<AddTeacherDto> teacherList){
        try {
            teacherService.batchSaveTeacherInfo(teacherList);
            return WrapMapper.ok();
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }

    }

    @ApiOperation(value = "根据学校Code查询所有老师卡号")
    @RequestMapping(value = "/queryTeacherCardNumberBySchoolCode", method = RequestMethod.POST)
    public Object queryTeacherCardNumberBySchoolCode(@RequestParam("schoolCode") String schoolCode) {
        try {
            return WrapMapper.ok(teacherService.queryTeacherCardNumberBySchoolCode(schoolCode));
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询出某个部门下面的老师
     * @param schoolCode
     * @param parentIds
     * @return
     */
    @ApiOperation(value = "查询出某个部门下面的老师")
    @RequestMapping(value = "/findTeacherInfoByDeptOrg", method = RequestMethod.POST)
    public Object findTeacherInfoByDeptOrg(@RequestParam("schoolCode")String schoolCode,@RequestParam("parentIds")String parentIds) {
        try {
            return WrapMapper.ok(teacherService.findTeacherInfoByDeptOrg(schoolCode,parentIds));
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询所有老师
     *
     * @return
     */
    @ApiOperation(value = "查询所有老师")
    @RequestMapping(value = "/findAllTeacher", method = RequestMethod.GET)
    public Object findAllTeacher() {
        try {
            return WrapMapper.ok(teacherService.selectAll());
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }
}
