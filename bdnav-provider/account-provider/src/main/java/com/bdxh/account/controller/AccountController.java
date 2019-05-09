package com.bdxh.account.controller;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.dto.AddAccountDto;
import com.bdxh.account.dto.UpdateAccountDto;
import com.bdxh.account.entity.Account;
import com.bdxh.account.service.AccountService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 账户管理控制器
 * @author: xuyuan
 * @create: 2019-03-06 10:38
 **/
@RestController
@RequestMapping("/account")
@Slf4j
@Validated
@Api(value = "账户管理接口文档", tags = "账户管理接口文档")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @ApiOperation(value = "增加账户信息", response = Boolean.class)
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public Object addAccount(@Valid @RequestBody AddAccountDto addAccountDto) {
        Account account = new Account();
        account.setSchoolCode(addAccountDto.getSchoolCode());
        account.setCardNumber(addAccountDto.getCardNumber());
        Account accountData = accountService.selectOne(account);
        Preconditions.checkArgument(accountData == null, "账户已存在");
        BeanMapUtils.copy(addAccountDto, account);
        account.setId(snowflakeIdWorker.nextId());
        return WrapMapper.ok(accountService.save(account) > 0);
    }

    @ApiOperation(value = "修改账户信息", response = Boolean.class)
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public Object updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto) {
        Account account = new Account();
        BeanUtils.copyProperties(updateAccountDto, account);
        return WrapMapper.ok(accountService.updateAccount(account));
    }

    @ApiOperation(value = "修改账户登录名", response = Boolean.class)
    @RequestMapping(value = "/updateLoginName", method = RequestMethod.POST)
    public Object updateLoginName(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                  @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber,
                                  @RequestParam("loginName") @NotEmpty(message = "登录名不能为空") String loginName) {
        accountService.updateLoginName(schoolCode, cardNumber, loginName);
        return WrapMapper.ok();
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
    public Object queryAccountList(@Valid @RequestBody AccountQueryDto accountQueryDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountQueryDto, account);
        List<Account> accounts = accountService.queryAccountList(account);
        return WrapMapper.ok(accounts);
    }

    @ApiOperation(value = "分页查询账户信息列表", response = Account.class)
    @RequestMapping(value = "/queryAccountListPage", method = RequestMethod.POST)
    public Object queryCategoryListPage(@Valid @RequestBody AccountQueryDto accountQueryDto) {
        PageInfo<Account> accounts = accountService.queryAccountListPage(accountQueryDto);
        return WrapMapper.ok(accounts);
    }

}
