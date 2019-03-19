package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.feign.FamilyStudentControllerClient;
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

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:02
 **/
@RestController
@RequestMapping("/familyStudent")
@Validated
@Slf4j
@Api(value = "家长学生关系交互API", tags = "家长学生关系交互API")
public class FamilyStudentController {
    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    /**
     * 家长绑定孩子
     * @param addFamilyStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value="家长绑定孩子接口")
    @RequestMapping(value = "/bindingStudent",method = RequestMethod.POST)
    public Object bindingStudent(@Valid @RequestBody AddFamilyStudentDto addFamilyStudentDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            familyStudentControllerClient.bindingStudent(addFamilyStudentDto);
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
            familyStudentControllerClient.removeFamilyOrStudent(schoolCode, cardNumber,id);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}