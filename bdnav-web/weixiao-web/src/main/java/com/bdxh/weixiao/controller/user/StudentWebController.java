package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-19 14:13
 **/
@Slf4j
@RestController
@Validated
@Api(value = "学生信息控制器",tags = "学生相关信息")
@RequestMapping(value = "/studentWeb")
public class StudentWebController {

    @Autowired
    private StudentControllerClient studentControllerClient;

    @RequestMapping(value = "/getStudentInfoByCardNumber", method = RequestMethod.GET)
    @ApiOperation(value = "根据卡号获取学生信息", response = StudentVo.class)
    public Object getStudentInfoByCardNumber(@RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        return studentControllerClient.queryStudentInfo(userInfo.getSchoolCode(), cardNumber);
    }
}