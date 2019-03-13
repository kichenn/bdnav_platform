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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("增加账户信息")
    @RequestMapping("/addAccount")
    public Object addAccount(@Valid @RequestBody AddAccountDto addAccountDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Account account = new Account();
            account.setSchoolCode(addAccountDto.getSchoolCode());
            account.setCardNumber(addAccountDto.getCardNumber());
            Account accountData = accountService.selectOne(account);
            Preconditions.checkArgument(accountData == null,"账户已存在");
            BeanMapUtils.copy(addAccountDto, account);
            account.setId(snowflakeIdWorker.nextId());
            accountService.save(account);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("修改账户信息")
    @RequestMapping("/updateAccount")
    public Object updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(updateAccountDto);
            accountService.updateAccount(param);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("修改账户登录名")
    @RequestMapping("/updateLoginName")
    public Object updateLoginName(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                  @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber,
                                  @RequestParam("loginName") @NotEmpty(message = "登录名不能为空") String loginName){
        try {
            accountService.updateLoginName(schoolCode,cardNumber,loginName);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询账户信息")
    @RequestMapping("/queryAccount")
    public Object queryAccount(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                               @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber){
        try {
            Account account = accountService.queryAccount(schoolCode,cardNumber);
            return WrapMapper.ok(account);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("查询账户信息列表")
    @RequestMapping("/queryAccountList")
    public Object queryAccountList(@Valid @RequestBody AccountQueryDto accountQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(accountQueryDto);
            List<Account> accounts = accountService.queryAccountList(param);
            return WrapMapper.ok(accounts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("分页查询账户信息列表")
    @RequestMapping("/queryAccountListPage")
    public Object queryCategoryListPage(@Valid @RequestBody AccountQueryDto accountQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(accountQueryDto);
            PageInfo<Account> accounts = accountService.queryAccountListPage(param,accountQueryDto.getPageNum(),accountQueryDto.getPageSize());
            return WrapMapper.ok(accounts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
