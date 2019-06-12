package com.bdxh.account.service;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.dto.AddAccountDto;
import com.bdxh.account.dto.UpdateAccountDto;
import com.bdxh.account.entity.Account;
import com.bdxh.common.support.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 账户管理service
 * @Author: Kang
 * @Date: 2019/5/9 17:04
 */
public interface AccountService extends IService<Account> {

    /**
     * 添加账户信息
     */
    Boolean addAccount(AddAccountDto addAccountDto);

    /**
     * 修改账户信息
     * @param updateAccountDto
     * @return
     */
    Boolean updateAccount(UpdateAccountDto updateAccountDto);

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
     * 手机号或者用户昵称查询账户信息
     *
     * @return
     */
    Account findAccountByLoginNameOrPhone(String phone, String loginName);

    /**
     * 修改用户名
     *
     * @param schoolCode
     * @param cardNumber
     * @param loginName
     */
    boolean updateLoginName(String schoolCode, String cardNumber, String loginName);


    /**
     * 根据登录名或者手机号修改密码
     *
     * @return
     */
    boolean modifyPwd(String phone, String loginName, String pwd) throws Exception;


    /**
     * 存在就修改不存在就新增
     * @param account
     * @return
     */
    Boolean updateOrInsertAccount(Account account);
}
