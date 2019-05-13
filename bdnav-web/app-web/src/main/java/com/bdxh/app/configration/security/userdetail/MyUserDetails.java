package com.bdxh.app.configration.security.userdetail;

import com.bdxh.account.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
* @Description:   
* @Author: Kang
* @Date: 2019/5/10 15:04
*/
public class MyUserDetails implements UserDetails {


    private Long id;
    private String loginname;

    private String password;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private Account account;

    public MyUserDetails(Long id,String loginname, String password, boolean isAccountNonExpired, boolean isAccountNonLocked, Account account) {
        this.id = id;
        this.loginname = loginname;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.account = account;
    }

    public Long getId(){
        return id;
    }

    @Override
    public String getUsername() {
        return loginname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
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
        return null;
    }

    public Account getAccount() {
        return account;
    }

}
