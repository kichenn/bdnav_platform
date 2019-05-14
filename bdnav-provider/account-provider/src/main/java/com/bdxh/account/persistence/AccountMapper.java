package com.bdxh.account.persistence;

import com.bdxh.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface AccountMapper extends Mapper<Account> {

    /**
     * 根据条件查询账户信息列表
     *
     * @return
     */
    List<Account> getByCondition(@Param("account") Account account);

    /**
     * 查询账户信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    Account getAccount(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);


    /**
     * 手机号或者登录名查询用户信息
     *
     * @param phone
     * @param loginName
     * @return
     */
    Account findAccountByLoginNameOrPhone(@Param("phone") String phone, @Param("loginName") String loginName);


    /**
     * 修改用户名
     *
     * @param schoolCode
     * @param cardNumber
     * @param loginName
     */
    int updateLoginName(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber, @Param("loginName") String loginName);

    /**
     * 更新账户信息
     */
    int updateAccount(@Param("account") Account account);


    /**
     * 根据登录名或者手机号修改密码
     *
     * @return
     */
    int modifyPwd(@Param("phone") String phone,@Param("loginName") String loginName,@Param("pwd") String pwd);
}