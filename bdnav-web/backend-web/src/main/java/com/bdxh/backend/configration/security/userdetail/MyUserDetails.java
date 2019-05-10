package com.bdxh.backend.configration.security.userdetail;

import com.bdxh.system.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @description:
 * @author: Kang
 * @create: 2019-02-28 14:20
 **/
public class MyUserDetails implements UserDetails {

    private String username;

    private String password;

    private boolean isAccountNonLocked;

    private Collection<? extends GrantedAuthority> authorities;

    private User user;

    public MyUserDetails(String username, String password, boolean isAccountNonLocked, Collection<? extends GrantedAuthority> authorities, User user) {
        this.username = username;
        this.password = password;
        this.isAccountNonLocked = isAccountNonLocked;
        this.authorities = authorities;
        this.user = user;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return authorities;
    }

    public User getUser(){
        return user;
    }

}
