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
     * @param param
     * @return
     */
    List<Account> getByCondition(Map<String,Object> param);

    /**
     * 查询账户信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    Account getAccount(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);


    /**
     * 修改用户名
     * @param schoolCode
     * @param cardNumber
     * @param loginName
     */
    int updateLoginName(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("loginName") String loginName);

    /**
     * 更新账户信息
     * @param param
     */
    void updateAccount(Map<String,Object> param);

}