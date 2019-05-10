package com.bdxh.backend.controller.user;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.FamilyStudentDetailsVo;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.user.vo.StudentVo;
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

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:02
 **/
@RestController
@RequestMapping("/familyStudentWeb")
@Validated
@Slf4j
@Api(value = "家长学生关系交互API", tags = "家长学生关系交互API")
public class FamilyStudentWebController {
    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @Autowired
    private FamilyControllerClient familyControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;


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
            FamilyStudentQueryDto familyStudentQueryDto=new FamilyStudentQueryDto();
            familyStudentQueryDto.setStudentNumber(addFamilyStudentDto.getStudentNumber());
            familyStudentQueryDto.setSchoolCode(addFamilyStudentDto.getSchoolCode());
            Wrapper wrapper =familyStudentControllerClient.queryAllFamilyStudent(familyStudentQueryDto);
            PageInfo pageInfo=(PageInfo)wrapper.getResult();
            if(pageInfo.getTotal()!=0){
                return WrapMapper.error("该学生已存在绑定关系");
            }
            User user= SecurityUtils.getCurrentUser();
            addFamilyStudentDto.setOperator(user.getId());
            addFamilyStudentDto.setOperatorName(user.getUserName());
            Wrapper wrappers =familyStudentControllerClient.bindingStudent(addFamilyStudentDto);
            return wrappers;
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

    /**
     * 查询所有关系
     * @param familyStudentQueryDto
     * @return
     */
    @ApiOperation(value = "查询所有家长与孩子关系")
    @RequestMapping(value = "/queryAllFamilyStudent",method =RequestMethod.POST)
    public Object queryAllFamilyStudent(@RequestBody FamilyStudentQueryDto familyStudentQueryDto){
        try{
            Wrapper wrapper =familyStudentControllerClient.queryAllFamilyStudent(familyStudentQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value = "查询家长与孩子关系详细")
    @RequestMapping(value = "/queryFamilyStudentDetails",method =RequestMethod.POST)
    public Object queryFamilyStudentDetails(@RequestBody FamilyStudentQueryDto familyStudentQueryDto){
        try{
            FamilyVo familyVo=familyControllerClient.queryFamilyInfo(familyStudentQueryDto.getSchoolCode(),familyStudentQueryDto.getCardNumber()).getResult();
            StudentVo studentVo= studentControllerClient.queryStudentInfo(familyStudentQueryDto.getSchoolCode(),familyStudentQueryDto.getStudentNumber()).getResult();
            FamilyStudentDetailsVo familyStudentDetailsVo=new FamilyStudentDetailsVo();
            familyStudentDetailsVo.setFamilyVo(familyVo);
            familyStudentDetailsVo.setStudentVo(studentVo);
            return WrapMapper.ok(familyStudentDetailsVo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}