package com.bdxh.account.service.impl;

import com.bdxh.account.dto.AccountQueryDto;
import com.bdxh.account.dto.AddAccountDto;
import com.bdxh.account.dto.UpdateAccountDto;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.persistence.AccountMapper;
import com.bdxh.account.service.AccountService;
import com.bdxh.account.service.AccountUnqiueService;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.HypyUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private AccountUnqiueService accountUnqiueService;

    /**
     * 添加账户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAccount(AddAccountDto addAccountDto) {
        long id = snowflakeIdWorker.nextId();
        //组装全局字典表
        AccountUnqiue accountUnqiue = new AccountUnqiue();
        accountUnqiue.setId(id);
        accountUnqiue.setLoginName(addAccountDto.getLoginName());
        accountUnqiue.setPhone(addAccountDto.getUserPhone());
        accountUnqiue.setCardNumber(addAccountDto.getCardNumber());
        accountUnqiue.setSchoolCode(addAccountDto.getSchoolCode());
        accountUnqiueService.addAccountUnqiue(accountUnqiue);
        //增加账户信息
        Account account = new Account();
        BeanMapUtils.copy(addAccountDto, account);
        //密码加密
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        account.setId(id);
        return accountMapper.insertSelective(account) > 0;
    }

    /**
     * 修改账户信息
     *
     * @param updateAccountDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAccount(UpdateAccountDto updateAccountDto) {
        //组装全局字典表
        AccountUnqiue accountUnqiue = new AccountUnqiue();
        accountUnqiue.setPhone(updateAccountDto.getUserPhone());
        accountUnqiue.setCardNumber(updateAccountDto.getCardNumber());
        accountUnqiue.setSchoolCode(updateAccountDto.getSchoolCode());
        accountUnqiueService.modifyAccountUnqiue(accountUnqiue);
        //修改账户信息
        Account account = new Account();
        BeanUtils.copyProperties(updateAccountDto, account);
        //密码加密
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        return accountMapper.updateAccount(account) > 0;
    }

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
    public boolean updateLoginName(String schoolCode, String cardNumber, String loginName) {
        int result = accountMapper.updateLoginName(schoolCode, cardNumber, loginName);
        Preconditions.checkArgument(result == 1, "修改用户名失败");
        return result > 0;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrInsertAccount(Account account) {
        Account oldAccount=accountMapper.getAccount(account.getSchoolCode(),account.getCardNumber());
       Boolean result;
        if(null==oldAccount){
            AccountUnqiue accountUnqiue=new AccountUnqiue();
            accountUnqiue.setCardNumber(account.getCardNumber());
            accountUnqiue.setId(account.getId());
            accountUnqiue.setLoginName(account.getLoginName());
            accountUnqiue.setCardNumber(account.getCardNumber());
            accountUnqiue.setPhone(account.getUserPhone());
            accountUnqiue.setSchoolCode(account.getSchoolCode());
            try {
                accountUnqiueService.addAccountUnqiue(accountUnqiue);
            }catch (Exception e){
                log.info("添加失败存在相同的账号信息");
                return false;
            }
            result=accountMapper.insert(account)>0;
        }else{
            Account newAccount=BeanMapUtils.map(oldAccount,Account.class);
            result=accountMapper.updateAccount(newAccount)>0;
        }
        return result;
    }
}
