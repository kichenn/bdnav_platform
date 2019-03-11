package com.bdxh.app.configration.security.userdetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 14:32
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
        MyUserDetails myUserDetails = new MyUserDetails(username,password,true,true,"测试数据");
        return myUserDetails;
    }

}
