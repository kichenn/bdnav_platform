package com.bdxh.weixiao.configration.security.userdetail;

import com.bdxh.account.entity.Account;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/5/10 15:04
 */
public class MyUserDetails implements UserDetails {

    private String cardNumber;

    private String password;

    private UserInfo userInfo;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(String cardNumber, UserInfo userInfo) {
        this.cardNumber = cardNumber;
        this.password = "";
        this.userInfo = userInfo;
    }

    @Override
    public String getUsername() {
        return cardNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

}
