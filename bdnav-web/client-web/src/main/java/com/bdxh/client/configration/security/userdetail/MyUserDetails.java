package com.bdxh.client.configration.security.userdetail;

import com.bdxh.school.entity.SchoolUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
* @Description:  学校用户
* @Author: Kang
* @Date: 2019/4/8 10:28
*/
public class MyUserDetails implements UserDetails {

    private String username;

    private String password;

    private boolean isAccountNonLocked;

    private Collection<? extends GrantedAuthority> authorities;

    private SchoolUser schoolUser;

    public MyUserDetails(String username, String password, boolean isAccountNonLocked, Collection<? extends GrantedAuthority> authorities, SchoolUser schoolUser) {
        this.username = username;
        this.password = password;
        this.isAccountNonLocked = isAccountNonLocked;
        this.authorities = authorities;
        this.schoolUser = schoolUser;
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

    public SchoolUser getSchoolUser(){
        return schoolUser;
    }

}
