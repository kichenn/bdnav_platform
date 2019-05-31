package com.bdxh.app.configration.security.utils;

import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public static Account getCurrentUser() {
        Account account = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            account = myUserDetails.getAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public static String getLoginName() {
        String loginName = null;
        try {
            Account account = getCurrentUser();
            loginName = account.getLoginName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginName;
    }

    /**
     * 获取当前登录用户姓名
     *
     * @return
     */
    public static String getRealName() {
        String realName = null;
        try {
            Account account = getCurrentUser();
            realName = account.getUserName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realName;
    }

}
