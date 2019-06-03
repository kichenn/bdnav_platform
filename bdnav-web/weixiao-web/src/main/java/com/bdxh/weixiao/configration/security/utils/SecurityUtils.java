package com.bdxh.weixiao.configration.security.utils;

import com.bdxh.account.entity.Account;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.userdetail.MyUserDetails;
import com.bdxh.weixiao.configration.security.userdetail.WeixiaoGrantedAuthority;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用户信息工具类
 * @Author: Kang
 * @Date: 2019/5/10 15:04
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static UserInfo getCurrentUser() {
        UserInfo account = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            account = myUserDetails.getUserInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 获取当前用户权限信息
     *
     * @return
     */
    public static List<WeixiaoGrantedAuthority> getCurrentAuthorized() {
        List<WeixiaoGrantedAuthority> authorizeds = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            authorizeds = (List<WeixiaoGrantedAuthority>) myUserDetails.getAuthorities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizeds;
    }

    /**
     * 获取myUserDetails
     *
     * @return
     */
    public static MyUserDetails getMyUserDetails() {
        MyUserDetails myUserDetails = null;
        try {
            myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myUserDetails;
    }
}
