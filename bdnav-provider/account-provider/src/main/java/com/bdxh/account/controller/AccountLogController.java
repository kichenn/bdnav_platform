package com.bdxh.account.controller;

import com.bdxh.account.dto.*;
import com.bdxh.account.entity.AccountLog;
import com.bdxh.account.service.AccountLogService;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description: 账户日志管理控制器
 * @Author: Kang
 * @Date: 2019/5/9 17:04
 */
@RestController
@RequestMapping("/accountLog")
@Slf4j
@Validated
@Api(value = "账户日志管理控制器", tags = "账户日志管理控制器")
public class AccountLogController {

    @Autowired
    private AccountLogService accountLogService;


    @ApiOperation(value = "增加账户登录日志信息", response = Boolean.class)
    @RequestMapping(value = "/addAccountLog", method = RequestMethod.POST)
    public Object addAccountLog(@Validated @RequestBody AddAccountLogDto addAccountLogDto) {
        AccountLog accountLog = new AccountLog();
        BeanUtils.copyProperties(addAccountLogDto, accountLog);
        accountLog.setCreateDate(new Date());
        accountLogService.addAcountLog(accountLog);
        return WrapMapper.ok();
    }

    @ApiOperation(value = "学校code，用户学号查询账户最后一次登录信息", response = AccountLog.class)
    @RequestMapping(value = "/findAcountLogByCodeAndCard", method = RequestMethod.GET)
    public Object findAcountLogByCodeAndCard(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        return WrapMapper.ok(accountLogService.findAccountLogBySchoolCodeAndCardNumber(schoolCode, cardNumber));
    }

    @ApiOperation(value = "登录名查询账户最后一次登录信息", response = AccountLog.class)
    @RequestMapping(value = "/findAcountLogByLoginName", method = RequestMethod.GET)
    public Object findAcountLogByLoginName(@RequestParam("loginName") String loginName) {
        return WrapMapper.ok(accountLogService.findAccountLogByLoginName(loginName));
    }

    @ApiOperation(value = "userId查询账户最后一次登录信息", response = AccountLog.class)
    @RequestMapping(value = "/findAcountLogByUserId", method = RequestMethod.GET)
    public Object findAcountLogByUserId(@RequestParam("userId") String userId) {
        return WrapMapper.ok(accountLogService.findAccountLogByUserId(userId));
    }



}
