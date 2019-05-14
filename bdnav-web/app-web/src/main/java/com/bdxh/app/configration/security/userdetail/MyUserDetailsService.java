package com.bdxh.app.configration.security.userdetail;

import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/5/10 15:04
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountControllerClient accountControllerClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断是否为手机号
        boolean isPhone = ValidatorUtil.isMobile(username);
        //效验该登录账号是手机号还是登录名信息
        String phone = isPhone ? username : "";
        String loginName = isPhone ? "" : username;
        Wrapper<Account> wrapper = accountControllerClient.findAccountByLoginNameOrPhone(phone, loginName);
        Account account = wrapper.getResult();
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //true为未过期,false  已过期
        boolean isAccountNonExpired = account.getAccountExpired() == 1 ? Boolean.TRUE : Boolean.FALSE;
        //true为未锁定,false 已锁定
        boolean isAccountNonLocked = account.getAccountLocked() == 1 ? Boolean.TRUE : Boolean.FALSE;
        MyUserDetails myUserDetails = new MyUserDetails(account.getId(), username, account.getPassword(), isAccountNonExpired, isAccountNonLocked, account);
        return myUserDetails;
    }

}
