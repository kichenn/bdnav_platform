package com.bdxh.app.configration.security.userdetail;

import com.bdxh.account.entity.Account;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = new Account();
        account.setId(12345L);
        account.setUserName(username);
        account.setLoginName(username);
        account.setPassword("");
        MyUserDetails myUserDetails = new MyUserDetails(12345L,username,new BCryptPasswordEncoder().encode("123456"),true,true, account);
        return myUserDetails;
    }

}
