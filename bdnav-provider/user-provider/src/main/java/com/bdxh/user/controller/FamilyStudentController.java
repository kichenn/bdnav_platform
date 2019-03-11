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
import com.bdxh.user.configration.idgenerator.IdGeneratorProperties;
import com.bdxh.user.dto.FamilyStudentDto;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.service.FamilyStudentService;
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

    /**
     * @Author： binzh
     * @Description： //家长绑定孩子接口
     * @Date： 14:51 2019/3/5
     * @Param： [familyStudentDto, bindingResult]
     * @return： java.lang.Object
     **/
    @ApiOperation(value="家长绑定孩子接口")
    @RequestMapping(value = "/bindingStudent",method = RequestMethod.POST)
    public Object bindingStudent(@Valid @RequestBody FamilyStudentDto familyStudentDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            FamilyStudent familyStudent = BeanMapUtils.map(familyStudentDto, FamilyStudent.class);
            IdGeneratorProperties idGeneratorProperties=new IdGeneratorProperties();
            familyStudent.setId(new SnowflakeIdWorker(idGeneratorProperties.getWorkerId(),idGeneratorProperties.getDatacenterId()).nextId());
            familyStudentService.save(familyStudent);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * @Author： binzh
     * @Description： //删除学生家长绑定关系
     * @Date： 15:01 2019/3/5
     * @Param： [id]
     * @return： java.lang.Object
     **/
    @ApiOperation(value = "删除学生家长绑定关系")
    @RequestMapping(value = "/removeFamilyOrStudent",method = RequestMethod.DELETE)
    public Object removeFamilyOrStudent(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                                        @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber){
        try{
            familyStudentService.removeFamilyStudentInfo(schoolCode, cardNumber);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
