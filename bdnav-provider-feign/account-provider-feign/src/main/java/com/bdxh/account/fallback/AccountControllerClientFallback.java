package com.bdxh.account.fallback;

import com.bdxh.account.dto.*;
import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Component
public class AccountControllerClientFallback implements AccountControllerClient {

    @Override
    public Wrapper addAccount(AddAccountDto addAccountDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateAccount(UpdateAccountDto updateAccountDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateLoginName(String schoolCode, String cardNumber, String loginName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyPwd(ModifyAccountPwdDto modifyAccountPwdDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper forgetPwd(ForgetPwd forgetPwd) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Account> queryAccount(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Account>> queryAccountList(AccountQueryDto accountQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<Account>> queryCategoryListPage(AccountQueryDto accountQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Account> findAccountByLoginNameOrPhone(String phone, String loginName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper getCaptcha(String phone) {
        return WrapMapper.error();
    }
}