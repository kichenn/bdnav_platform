package com.bdxh.account.fallback;

import com.bdxh.account.dto.AddAccountLogDto;
import com.bdxh.account.entity.AccountLog;
import com.bdxh.account.feign.AccountLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description: 登录日志相关熔断
 * @Author: Kang
 * @Date: 2019/5/16 16:45
 */
@Component
public class AccountLogControllerClientFallback implements AccountLogControllerClient {

    @Override
    public Wrapper addAccountLog(AddAccountLogDto addAccountLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AccountLog> findAcountLogByCodeAndCard(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AccountLog> findAcountLogByLoginName(String loginName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AccountLog> findAcountLogByUserId(String userId) {
        return WrapMapper.error();
    }

}
