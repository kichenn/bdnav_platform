/**
 * Copyright (C), 2019-2019
 * FileName: FamilyStudentController
 * Author:   binzh
 * Date:     2019/3/5 14:01
 * Description: TOOO
 * History:
 */
package com.bdxh.user.controller;

import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.RandomUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.configration.redis.RedisUtil;
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
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "家长学生绑定接口API", tags = "家长学生绑定接口API")
@RestController
@RequestMapping("/familyStudent")
@Validated
@Slf4j
public class FamilyStudentController {

    @Autowired
    private FamilyStudentService familyStudentService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 绑定孩子接口
     *
     * @param addFamilyStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "绑定孩子接口")
    @RequestMapping(value = "/bindingStudent", method = RequestMethod.POST)
    public Object bindingStudent(@Valid @RequestBody AddFamilyStudentDto addFamilyStudentDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
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
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value = "删除学生家长绑定关系")
    @RequestMapping(value = "/removeFamilyOrStudent", method = RequestMethod.GET)
    public Object removeFamilyOrStudent(@RequestParam(name = "schoolCode") @NotNull(message = "学校Code不能为空") String schoolCode,
                                        @RequestParam(name = "cardNumber") @NotNull(message = "微校卡号不能为空") String cardNumber,
                                        @RequestParam(name = "id") @NotNull(message = "id不能为空") String id) {
        try {
            familyStudentService.removeFamilyStudentInfo(schoolCode, cardNumber, id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询所有家长与孩子关系
     *
     * @param familyStudentQueryDto
     * @return
     */
    @ApiOperation(value = "查询所有家长与孩子关系")
    @RequestMapping(value = "/queryAllFamilyStudent", method = RequestMethod.POST)
    public Object queryAllFamilyStudent(@RequestBody FamilyStudentQueryDto familyStudentQueryDto) {
        try {
            PageInfo<FamilyStudentVo> familyStudentVoPageInfo = familyStudentService.queryAllFamilyStudent(familyStudentQueryDto);
            return WrapMapper.ok(familyStudentVoPageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 家长卡号 +学校code  查询绑定关系
     *
     * @param familyCardNumber
     * @return
     */
    @ApiOperation(value = "家长卡号查询绑定关系", response = FamilyStudentVo.class)
    @RequestMapping(value = "/queryStudentByFamilyCardNumber", method = RequestMethod.GET)
    public Object queryStudentByFamilyCardNumber(@RequestParam("schoolCode") String schoolCode, @RequestParam("familyCardNumber") String familyCardNumber) {
        List<FamilyStudentVo> familyStudent = familyStudentService.queryStudentByFamilyCardNumber(schoolCode, familyCardNumber);
        return WrapMapper.ok(familyStudent);
    }

    /**
     * @Description: 学生卡号 +学校code 查询绑定关系
     * @Author: Kang
     * @Date: 2019/6/1 10:10
     */
    @ApiOperation(value = "学生卡号 +学校code 查询绑定关系", response = FamilyStudentVo.class)
    @RequestMapping(value = "/studentQueryInfo", method = RequestMethod.GET)
    public Object studentQueryInfo(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        FamilyStudentVo familyStudent = familyStudentService.studentQueryInfo(schoolCode, cardNumber);
        return WrapMapper.ok(familyStudent);
    }

    @ApiOperation(value = "微校平台----手机获取短信验证码")
    @RequestMapping(value = "/getPhoneCode", method = RequestMethod.POST)
    public Object getPhoneCode(@RequestParam(name = "phone") @NotNull(message = "手机号码不能为空") String phone) {
        if (!ValidatorUtil.isMobile(phone)) {
            return WrapMapper.error("请输入正确的手机号");
        }
        //生成随机数
        String code = RandomUtil.createNumberCode(4);
        redisUtil.setWithExpireTime(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX + phone, code, AliyunSmsConstants.CodeConstants.CAPTCHA_TIME);
        SmsUtil.sendMsgHelper(SmsTempletEnum.TEMPLATE_VERIFICATION, phone, code + ",:绑定孩子");
        return WrapMapper.ok(Boolean.TRUE);
    }
}
