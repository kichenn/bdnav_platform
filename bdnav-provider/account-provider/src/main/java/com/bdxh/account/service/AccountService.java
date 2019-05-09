package com.bdxh.account.service;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.entity.Account;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 账户管理service
 * @author: xuyuan
 * @create: 2019-03-04 17:31
 **/
public interface AccountService extends IService<Account> {

    /**
     * 查询账户信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    Account queryAccount(String schoolCode, String cardNumber);


    /**
     * 根据条件查询账户列表
     *
     * @return
     */
    List<Account> queryAccountList(Account account);

    /**
     * 根据条件分页查询账户列表
     *
     * @return
     */
    PageInfo<Account> queryAccountListPage(AccountQueryDto accountQueryDto);

    /**
     * 修改用户名
     *
     * @param schoolCode
     * @param cardNumber
     * @param loginName
     */
    int updateLoginName(String schoolCode, String cardNumber, String loginName);

    /**
     * 更新账户信息
     */
    boolean updateAccount(Account account);

}
