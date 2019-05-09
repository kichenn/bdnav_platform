package com.bdxh.appburied.feign;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.dto.AddAccountDto;
import com.bdxh.account.dto.UpdateAccountDto;
import com.bdxh.account.entity.Account;
import com.bdxh.appburied.fallback.AccountControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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

}