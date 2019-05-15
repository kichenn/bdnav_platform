/**
 * Copyright (C), 2019-2019
 * FileName: FamilyStudentController
 * Author:   binzh
 * Date:     2019/3/5 14:01
 * Description: TOOO
 * History:
 */
package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.service.FamilyStudentService;
import com.bdxh.user.vo.FamilyStudentVo;
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
import java.util.stream.Collectors;

@Api(value ="家长学生绑定接口API", tags = "家长学生绑定接口API")
@RestController
@RequestMapping("/familyStudent")
@Validated
@Slf4j
public class FamilyStudentController {

@Autowired
private FamilyStudentService familyStudentService;
@Autowired
private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 绑定孩子接口
     * @param addFamilyStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="绑定孩子接口")
    @RequestMapping(value = "/bindingStudent",method = RequestMethod.POST)
    public Object bindingStudent(@Valid @RequestBody AddFamilyStudentDto addFamilyStudentDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            FamilyStudent familyStudent = BeanMapUtils.map(addFamilyStudentDto, FamilyStudent.class);

            familyStudent.setId(snowflakeIdWorker.nextId());
            familyStudentService.save(familyStudent);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除学生家长绑定关系
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value = "删除学生家长绑定关系")
    @RequestMapping(value = "/removeFamilyOrStudent",method = RequestMethod.GET)
    public Object removeFamilyOrStudent(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                                        @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber,
                                        @RequestParam(name = "id") @NotNull(message="id不能为空")String id){
        try{
            familyStudentService.removeFamilyStudentInfo(schoolCode, cardNumber,id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询所有家长与孩子关系
     * @param familyStudentQueryDto
     * @return
     */
    @ApiOperation(value = "查询所有家长与孩子关系")
    @RequestMapping(value = "/queryAllFamilyStudent",method =RequestMethod.POST)
    public Object queryAllFamilyStudent(@RequestBody FamilyStudentQueryDto familyStudentQueryDto) {
        try {
            PageInfo<FamilyStudentVo> familyStudentVoPageInfo = familyStudentService.queryAllFamilyStudent(familyStudentQueryDto);
            return WrapMapper.ok(familyStudentVoPageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
