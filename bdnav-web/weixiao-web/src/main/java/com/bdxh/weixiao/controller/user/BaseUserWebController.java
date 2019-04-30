package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 学校用户(学生 ， 家长 ， 老师)控制器
 * @author: binzh
 * @create: 2019-04-26 14:06
 **/
@Slf4j
@RequestMapping(value = "/baseUser")
@RestController
@Api(value = "微校平台----学校用户(学生 ， 家长 ， 老师)控制器")
@Validated
public class BaseUserWebController {
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;
    @Autowired
    private FamilyControllerClient familyControllerClient;
    @Autowired
    private StudentControllerClient studentControllerClient;
    @Autowired
    private TeacherControllerClient teacherControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    /**
     * 用户激活接口
     * @param
     * @return
     */
    @ApiOperation(value = "用户激活接口")
    @RequestMapping(value = "/activationBaseUser",method = RequestMethod.POST)
    public Object activationBaseUser(@RequestBody BaseUserQueryDto baseUserQueryDto){
        //查询出当前激活用户的类型 1、学生，2、家长，3、老师
      BaseUser baseUser=baseUserControllerClient.queryBaseUserInfoByPhone(baseUserQueryDto.getPhone()).getResult();
      School school=schoolControllerClient.findSchoolBySchoolCode(baseUser.getSchoolCode()).getResult();
      String appKey=school.getAppKey();
      String appSecret=school.getAppSecret();
      Byte schoolType=school.getSchoolType();
      String schoolCode=baseUser.getSchoolCode();
      String cardNumber=baseUser.getCardNumber();
        switch (baseUser.getUserType()){
          case 1:
                //学生激活
              UpdateStudentDto updateStudentDto=new UpdateStudentDto();
              updateStudentDto.setSchoolCode(schoolCode);
              updateStudentDto.setCardNumber(cardNumber);
              updateStudentDto.setAppKey(appKey);
              updateStudentDto.setAppKey(appSecret);
              updateStudentDto.setSchoolType(schoolType);
              updateStudentDto.setActivate(Byte.valueOf("2"));
             // studentControllerClient.activationStudent(updateStudentDto);
              break;
          case 2:
                //家长激活
              UpdateFamilyDto updateFamilyDto=new UpdateFamilyDto();
              updateFamilyDto.setSchoolCode(schoolCode);
              updateFamilyDto.setCardNumber(cardNumber);
              updateFamilyDto.setAppKey(appKey);
              updateFamilyDto.setAppKey(appSecret);
              updateFamilyDto.setSchoolType(schoolType);
              updateFamilyDto.setActivate(Byte.valueOf("2"));
             // familyControllerClient.activationFamily(updateFamilyDto);
              break;
          case 3:
                //老师激活
              UpdateTeacherDto updateTeacherDto=new UpdateTeacherDto();
              updateTeacherDto.setSchoolCode(schoolCode);
              updateTeacherDto.setCardNumber(cardNumber);
              updateTeacherDto.setAppKey(appKey);
              updateTeacherDto.setAppKey(appSecret);
              updateTeacherDto.setSchoolType(schoolType);
              updateTeacherDto.setActivate(Byte.valueOf("2"));
              //teacherControllerClient.activationTeacher(updateTeacherDto);
              break;
          default:
              return WrapMapper.error("激活身份异常,请联系管理员");
      }
      return WrapMapper.ok();
    }
}