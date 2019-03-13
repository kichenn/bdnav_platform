package com.bdxh.app.configration.security.utils;

import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @description: 用户信息工具类
 * @author: xuyuan
 * @create: 2019-02-28 16:22
 **/
public class SecurityUtils {

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static Account getCurrentUser(){
        Account account = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            account = myUserDetails.getAccount();
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getLoginName(){
        String loginName = null;
        try {
            Account account = getCurrentUser();
            loginName = account.getLoginName();
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginName;
    }

    /**
     * 获取当前登录用户姓名
     * @return
     */
    public static String getRealName(){
        String realName = null;
        try {
            Account account = getCurrentUser();
            realName = account.getUserName();
        }catch (Exception e){
            e.printStackTrace();
        }
        return realName;
    }

}
