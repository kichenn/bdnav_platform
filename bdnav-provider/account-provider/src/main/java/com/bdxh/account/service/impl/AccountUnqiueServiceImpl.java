package com.bdxh.account.service.impl;

import com.bdxh.account.service.AccountUnqiueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.persistence.AccountUnqiueMapper;

import java.util.List;


/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-05-15 10:48:22
 */
@Service
@Slf4j
public class AccountUnqiueServiceImpl extends BaseService<AccountUnqiue> implements AccountUnqiueService {

    @Autowired
    private AccountUnqiueMapper accountUnqiueMapper;

    @Override
    public boolean addAccountUnqiue(AccountUnqiue accountUnqiue) {
        return accountUnqiueMapper.addAccountUnqiue(accountUnqiue) > 0;
    }

    @Override
    public boolean delAccountUnqiue(String id) {
        return accountUnqiueMapper.delAccountUnqiue(id) > 0;
    }

    @Override
    public boolean modifyAccountUnqiue(AccountUnqiue accountUnqiue) {
        return accountUnqiueMapper.modifyAccountUnqiue(accountUnqiue) > 0;
    }

}
