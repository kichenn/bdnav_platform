package com.bdxh.client.controller.account;


import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.school.entity.SchoolUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


/**
 * @Description: 客户端账户管理信息
 * @Author: Kang
 * @Date: 2019/5/8 16:42
 */
@RestController
@RequestMapping("/clientAccountWeb")
@Slf4j
@Validated
@Api(value = "客户端账户管理信息", tags = "客户端账户管理信息交互API")
public class AccountWebController {

    @Autowired
    private AccountControllerClient accountControllerClient;

//    @ApiOperation(value = "增加账户信息", response = Boolean.class)
//    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
//    public Object addAccount(@Valid @RequestBody AddAccountDto addAccountDto) {
//        //设置账户学校信息
//        SchoolUser user = SecurityUtils.getCurrentUser();
//        addAccountDto.setSchoolId(user.getSchoolId());
//        addAccountDto.setSchoolCode(user.getSchoolCode());
//        return accountControllerClient.addAccount(addAccountDto);
//    }
//
//    @ApiOperation(value = "修改账户信息", response = Boolean.class)
//    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
//    public Object updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto) {
//        return accountControllerClient.updateAccount(updateAccountDto);
//    }
//
//    @ApiOperation(value = "修改账户登录名", response = Boolean.class)
//    @RequestMapping(value = "/updateLoginName", method = RequestMethod.POST)
//    public Object updateLoginName(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
//                                  @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber,
//                                  @RequestParam("loginName") @NotEmpty(message = "登录名不能为空") String loginName) {
//        return accountControllerClient.updateLoginName(schoolCode, cardNumber, loginName);
//    }

    @ApiOperation(value = "查询账户信息", response = Account.class)
    @RequestMapping(value = "/queryAccount", method = RequestMethod.GET)
    public Object queryAccount(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                               @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber) {
        return accountControllerClient.queryAccount(schoolCode, cardNumber);
    }

    @ApiOperation(value = "查询账户信息列表", response = Account.class)
    @RequestMapping(value = "/queryAccountList", method = RequestMethod.POST)
    public Object queryAccountList(@Valid @RequestBody AccountQueryDto accountQueryDto) {
        //设置账户学校信息
        SchoolUser user = SecurityUtils.getCurrentUser();
        accountQueryDto.setSchoolId(user.getSchoolId());
        accountQueryDto.setSchoolCode(user.getSchoolCode());
        return accountControllerClient.queryAccountList(accountQueryDto);
    }

    @ApiOperation(value = "分页查询账户信息列表", response = Account.class)
    @RequestMapping(value = "/queryAccountListPage", method = RequestMethod.POST)
    public Object queryCategoryListPage(@Valid @RequestBody AccountQueryDto accountQueryDto) {
        //设置账户学校信息
        SchoolUser user = SecurityUtils.getCurrentUser();
        accountQueryDto.setSchoolId(user.getSchoolId());
        accountQueryDto.setSchoolCode(user.getSchoolCode());
        return accountControllerClient.queryCategoryListPage(accountQueryDto);
    }
}
