package com.bdxh.account.service.impl;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.AccountLog;
import com.bdxh.account.persistence.AccountMapper;
import com.bdxh.account.persistence.mongodb.AccountLogMapper;
import com.bdxh.account.service.AccountLogService;
import com.bdxh.account.service.AccountService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: service实现
 * @Author: Kang
 * @Date: 2019/5/9 17:04
 */
@Service
@Slf4j
public class AccountLogServiceImpl implements AccountLogService {

    @Autowired
    private AccountLogMapper accountLogMapper;

    @Override
    public void addAcountLog(AccountLog accountLog) {
        accountLogMapper.addAccountLog(accountLog);
    }

    @Override
    public AccountLog findAccountLogBySchoolCodeAndCardNumber(String schoolCode, String cardNumber) {
        return accountLogMapper.findAccountLogBySchoolCodeAndCardNumber(schoolCode, cardNumber);
    }

    @Override
    public AccountLog findAccountLogByLoginName(String loginName) {
        return accountLogMapper.findAccountLogByLoginName(loginName);
    }

    @Override
    public AccountLog findAccountLogByUserId(String userId) {
        return accountLogMapper.findAccountLogByUserId(userId);
    }

}
