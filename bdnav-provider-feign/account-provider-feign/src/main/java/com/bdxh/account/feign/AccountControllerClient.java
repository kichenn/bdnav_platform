package com.bdxh.account.feign;

import com.bdxh.account.dto.*;
import com.bdxh.account.entity.Account;
import com.bdxh.account.fallback.AccountControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Description:
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@FeignClient(value = "account-provider-cluster", fallback = AccountControllerClientFallback.class)
public interface AccountControllerClient {

    /**
     * 增加账户信息
     */
    @RequestMapping(value = "/account/addAccount", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addAccount(@Valid @RequestBody AddAccountDto addAccountDto);

    /**
     * 修改账户信息
     */
    @RequestMapping(value = "/account/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateAccount(@Valid @RequestBody UpdateAccountDto updateAccountDto);

    /**
     * 修改账户登录名
     */
    @RequestMapping(value = "/account/updateLoginName", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateLoginName(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                            @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber,
                            @RequestParam("loginName") @NotEmpty(message = "登录名不能为空") String loginName);

    /**
     * 登录名修改密码
     *
     * @param modifyAccountPwdDto
     * @return
     */
    @RequestMapping(value = "/account/modifyPwd", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyPwd(@RequestBody @Validated ModifyAccountPwdDto modifyAccountPwdDto);

    /**
     * 忘记密码(根据手机号，验证码找回密码)
     *
     * @param forgetPwd
     * @return
     */
    @RequestMapping(value = "/account/forgetPwd", method = RequestMethod.POST)
    @ResponseBody
    Wrapper forgetPwd(@RequestBody @Validated ForgetPwd forgetPwd);

    /**
     * 查询账户信息
     */
    @RequestMapping(value = "/account/queryAccount", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<Account> queryAccount(@RequestParam("schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                  @RequestParam("cardNumber") @NotEmpty(message = "学号不能为空") String cardNumber);

    /**
     * 查询账户信息列表
     */
    @RequestMapping(value = "/account/queryAccountList", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<Account>> queryAccountList(@Valid @RequestBody AccountQueryDto accountQueryDto);

    /**
     * 分页查询账户信息列表
     */
    @RequestMapping(value = "/account/queryAccountListPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<Account>> queryCategoryListPage(@Valid @RequestBody AccountQueryDto accountQueryDto);


    /**
     * 用户名或者手机号查询账户信息
     *
     * @param phone
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/account/findAccountByLoginNameOrPhone", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<Account> findAccountByLoginNameOrPhone(@RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "loginName", required = false) String loginName);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/account/getCaptcha", method = RequestMethod.GET)
    @ResponseBody
    Wrapper getCaptcha(@RequestParam("phone") String phone);
}