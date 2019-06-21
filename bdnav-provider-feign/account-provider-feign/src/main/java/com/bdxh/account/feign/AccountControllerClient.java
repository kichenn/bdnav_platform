package com.bdxh.account.feign;

import com.bdxh.account.dto.*;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.fallback.AccountControllerClientFallback;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
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
    Wrapper addAccount(@Validated @RequestBody AddAccountDto addAccountDto);

    /**
     * 修改账户信息
     */
    @RequestMapping(value = "/account/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateAccount(@Validated @RequestBody UpdateAccountDto updateAccountDto);

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
    Wrapper modifyPwd(@Validated @RequestBody ModifyAccountPwdDto modifyAccountPwdDto);

    /**
     * 忘记密码(根据手机号，验证码找回密码)
     *
     * @param forgetPwd
     * @return
     */
    @RequestMapping(value = "/account/forgetPwd", method = RequestMethod.POST)
    @ResponseBody
    Wrapper forgetPwd(@Validated @RequestBody ForgetPwd forgetPwd);

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
    Wrapper<List<Account>> queryAccountList(@Validated @RequestBody AccountQueryDto accountQueryDto);

    /**
     * 分页查询账户信息列表
     */
    @RequestMapping(value = "/account/queryAccountListPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<Account>> queryCategoryListPage(@Validated @RequestBody AccountQueryDto accountQueryDto);


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
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/account/getCaptcha", method = RequestMethod.GET)
    @ResponseBody
    Wrapper getCaptcha(@RequestParam("phone") String phone);

    /**
     * 修改手机号码
     * @Author: WanMing
     * @Date: 2019/6/18 12:10
     */
    @RequestMapping(value = "/account/modifyPhone", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyPhone(@Validated @RequestBody ModifyAccountPhoneDto modifyAccountPhoneDto);

    /**
     * 验证密码
     * @Author: WanMing
     * @Date: 2019/6/21 11:19
     */
    @RequestMapping(value = "/account/verifyPassword", method = RequestMethod.GET)
    @ResponseBody
    Wrapper  verifyPassword(@RequestParam("password") String password,@RequestParam("loginName") String loginName);

    /**
     * 根据accountId查询 用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/account/findAccountInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<AccountUnqiue> findAccountInfo(@RequestParam("id")Long id);
}