package com.bdxh.account.service.impl;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.entity.Account;
import com.bdxh.account.persistence.AccountMapper;
import com.bdxh.account.service.AccountService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 账户管理service实现
 * @Author: Kang
 * @Date: 2019/5/9 17:04
 */
@Service
@Slf4j
public class AccountServiceImpl extends BaseService<Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account queryAccount(String schoolCode, String cardNumber) {
        Account account = accountMapper.getAccount(schoolCode, cardNumber);
        return account;
    }

    @Override
    public List<Account> queryAccountList(Account account) {
        List<Account> accounts = accountMapper.getByCondition(account);
        return accounts;
    }

    @Override
    public PageInfo<Account> queryAccountListPage(AccountQueryDto accountQueryDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accountQueryDto, account);
        PageHelper.startPage(accountQueryDto.getPageNum(), accountQueryDto.getPageSize());
        List<Account> accounts = accountMapper.getByCondition(account);
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        return pageInfo;
    }

    /**
     * 手机号或者用户昵称查询账户信息
     *
     * @return
     */
    @Override
    public Account findAccountByLoginNameOrPhone(String phone, String loginName) {
        return accountMapper.findAccountByLoginNameOrPhone(phone, loginName);
    }

    @Override
    public int updateLoginName(String schoolCode, String cardNumber, String loginName) {
        int result = accountMapper.updateLoginName(schoolCode, cardNumber, loginName);
        Preconditions.checkArgument(result == 1, "修改用户名失败");
        return result;
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountMapper.updateAccount(account) > 0;
    }

    /**
     * 根据登录名或者手机号修改密码
     *
     * @return
     */
    @Override
    public boolean modifyPwd(String phone, String loginName, String pwd) throws Exception {
        if (StringUtil.isEmpty(phone) && StringUtil.isEmpty(loginName)) {
            throw new Exception("手机号和登录名不能同时为空，请检查");
        }
        return accountMapper.modifyPwd(phone, loginName, pwd) > 0;
    }
}
