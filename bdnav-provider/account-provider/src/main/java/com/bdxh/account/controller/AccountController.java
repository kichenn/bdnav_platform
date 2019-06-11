package com.bdxh.account.controller;

import com.bdxh.account.configration.redis.RedisUtil;
import com.bdxh.account.dto.*;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.service.AccountService;
import com.bdxh.account.service.AccountUnqiueService;
import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Description: 账户管理控制器
 * @Author: Kang
 * @Date: 2019/5/9 17:04
 */
@RestController
@RequestMapping("/account")
@Slf4j
@Validated
@Api(value = "账户管理接口文档", tags = "账户管理接口文档")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountUnqiueService accountUnqiueService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "增加账户信息", response = Boolean.class)
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public Object addAccount(@Validated @RequestBody AddAccountDto addAccountDto) {
        try {
            return WrapMapper.ok(accountService.addAccount(addAccountDto));
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof DuplicateKeyException) {
                if (e.getMessage().contains("unqiue_login_name")) {
                    message = "该用户名已被人使用";
                } else if (e.getMessage().contains("unqiue_phone")) {
                    message = "该手机号已有账户";
                } else if (e.getMessage().contains("unqiue_schoolCode_cardNumber")) {
                    message = "该账户已存在";
                }
            }
            e.printStackTrace();
            return WrapMapper.error(message);
        }
    }

    @ApiOperation(value = "修改账户信息", response = Boolean.class)
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public Object updateAccount(@Validated @RequestBody UpdateAccountDto updateAccountDto) {
        try {
            return WrapMapper.ok(accountService.updateAccount(updateAccountDto));
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof DuplicateKeyException) {
                if (e.getMessage().contains("unqiue_login_name")) {
                    message = "该用户名已被人使用";
                }
            }
            e.printStackTrace();
            return WrapMapper.error(message);
        }
    }

    @ApiOperation(value = "修改账户登录名", response = Boolean.class)
    @RequestMapping(value = "/updateLoginName", method = RequestMethod.POST)
    public Object updateLoginName(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                  @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber,
                                  @RequestParam("loginName") @NotEmpty(message = "登录名不能为空") String loginName) {
        try {
            //组装全局字典表
            AccountUnqiue accountUnqiue = new AccountUnqiue();
            accountUnqiue.setLoginName(loginName);
            accountUnqiue.setCardNumber(cardNumber);
            accountUnqiue.setSchoolCode(schoolCode);
            accountUnqiueService.modifyAccountUnqiue(accountUnqiue);
            //修改登录名
            boolean result = accountService.updateLoginName(schoolCode, cardNumber, loginName);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof DuplicateKeyException) {
                if (e.getMessage().contains("unqiue_phone")) {
                    message = "该手机号已有账户";
                }
            }
            e.printStackTrace();
            return WrapMapper.error(message);
        }
    }

    @ApiOperation(value = "根据登录名修改密码", response = Boolean.class)
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public Object modifyPwd(@Validated @RequestBody ModifyAccountPwdDto modifyAccountPwdDto) {
        //登录名查询账户信息
        Account account = accountService.findAccountByLoginNameOrPhone("", modifyAccountPwdDto.getLoginName());
        try {
            //效验参数
            if (account == null) {
                throw new Exception("用户名错误，请检查");
            } else if (!ValidatorUtil.isPassword(modifyAccountPwdDto.getPwd())) {
                throw new Exception("密码格式错误,长度不可超过6 - 20");
            } else if (!modifyAccountPwdDto.getPwd().equals(modifyAccountPwdDto.getRePwd())) {
                throw new Exception("俩次输入的密码不同，请检查");
            } else if (!new BCryptPasswordEncoder().matches(modifyAccountPwdDto.getUsedPwd(), account.getPassword())) {
                //matches(第一个参数为前端传递的值，未加密，后一个为数据库已经加密的串对比)
                throw new Exception("旧密码错误，请检查");
            } else if (modifyAccountPwdDto.getPwd().equals(modifyAccountPwdDto.getUsedPwd())) {
                throw new Exception("新旧密码相同，请勿重复操作");
            }
            boolean result = accountService.modifyPwd(null, modifyAccountPwdDto.getLoginName(), new BCryptPasswordEncoder().encode(modifyAccountPwdDto.getPwd()));
            return WrapMapper.ok(result);
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value = "忘记密码(根据手机号，验证码找回密码)", response = Boolean.class)
    @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
    public Object forgetPwd(@Validated @RequestBody ForgetPwd forgetPwd) {
        try {
            //效验参数
            String captchaCode = redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX + forgetPwd.getPhone());
            if (StringUtil.isEmpty(captchaCode)) {
                throw new Exception("请先获取验证码");
            } else if (!ValidatorUtil.isPassword(forgetPwd.getPwd())) {
                throw new Exception("密码格式错误,长度不可超过6 - 20");
            } else if (!forgetPwd.getPwd().equals(forgetPwd.getRePwd())) {
                throw new Exception("俩次输入的密码不同，请检查");
            } else if (!forgetPwd.getCaptcha().equals(captchaCode)) {
                throw new Exception("验证码错误");
            }
            boolean result = accountService.modifyPwd(forgetPwd.getPhone(), forgetPwd.getLoginName(), new BCryptPasswordEncoder().encode(forgetPwd.getPwd()));
            return WrapMapper.ok(result);
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value = "查询账户信息", response = Account.class)
    @RequestMapping(value = "/queryAccount", method = RequestMethod.GET)
    public Object queryAccount(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                               @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber) {
        Account account = accountService.queryAccount(schoolCode, cardNumber);
        return WrapMapper.ok(account);
    }

    @ApiOperation(value = "查询账户信息列表", response = Account.class)
    @RequestMapping(value = "/queryAccountList", method = RequestMethod.POST)
    public Object queryAccountList(@Validated @RequestBody AccountQueryDto accountQueryDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountQueryDto, account);
        List<Account> accounts = accountService.queryAccountList(account);
        return WrapMapper.ok(accounts);
    }

    @ApiOperation(value = "分页查询账户信息列表", response = Account.class)
    @RequestMapping(value = "/queryAccountListPage", method = RequestMethod.POST)
    public Object queryCategoryListPage(@Validated @RequestBody AccountQueryDto accountQueryDto) {
        PageInfo<Account> accounts = accountService.queryAccountListPage(accountQueryDto);
        return WrapMapper.ok(accounts);
    }

    @ApiOperation(value = "用户名或者手机号查询账户信息", response = Account.class)
    @RequestMapping(value = "/findAccountByLoginNameOrPhone", method = RequestMethod.GET)
    public Object findAccountByLoginNameOrPhone(@RequestParam(value = "phone", required = false) String phone,
                                                @RequestParam(value = "loginName", required = false) String loginName) {
        //效验参数
        try {
            boolean expression = StringUtil.isEmpty(phone) && StringUtil.isEmpty(loginName);
            Preconditions.checkArgument(!expression, "请输入用户名或者手机号信息");
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
        return WrapMapper.ok(accountService.findAccountByLoginNameOrPhone(phone, loginName));
    }

    @ApiOperation(value = "获取验证码", response = Boolean.class)
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public Object getCaptcha(@RequestParam("phone") String phone) {
        if (!ValidatorUtil.isMobile(phone)) {
            return WrapMapper.error("请输入正确的手机号");
        }
        //生成随机数
        String code = RandomUtil.createNumberCode(4);
        redisUtil.setWithExpireTime(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX + phone, code, AliyunSmsConstants.CodeConstants.CAPTCHA_TIME);
        SmsUtil.sendMsgHelper(SmsTempletEnum.TEMPLATE_VERIFICATION, phone, code + ",:找回密码");
        return WrapMapper.ok(Boolean.TRUE);
    }

//    @ApiOperation(value = "导入账户数据", response = Boolean.class)
//    @RequestMapping(value = "/importAcountInfo", method = RequestMethod.POST)
//    public Object importAcountInfo(@RequestParam("accountFile") MultipartFile file) {
//        try {
//            List<String[]> studentList = ExcelImportUtil.readExcelNums(file, 0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return WrapMapper.ok();
//    }
}
