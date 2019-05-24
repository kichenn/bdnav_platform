package com.bdxh.account.service;

import com.bdxh.account.entity.AccountLog;

import java.util.List;

/**
 * @Description: 账户service
 * @Author: Kang
 * @Date: 2019/5/16 15:31
 */
public interface AccountLogService {

    /**
     * 新增账户日志信息
     *
     * @param accountLog
     */
    void addAcountLog(AccountLog accountLog);


    /**
     * 查询用户最近一条登录日志信息 (学校code+学号)
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    AccountLog findAccountLogBySchoolCodeAndCardNumber(String schoolCode, String cardNumber);


    /**
     * 查询用户最近一条登录日志信息(loginName)
     *
     * @param loginName
     * @return
     */
    public AccountLog findAccountLogByLoginName(String loginName);

    /**
     * 查询用户最近一条登录日志信息(userId)
     *
     * @param userId
     * @return
     */
    AccountLog findAccountLogByUserId(String userId);



}
