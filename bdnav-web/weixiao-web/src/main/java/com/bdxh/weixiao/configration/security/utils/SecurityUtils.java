package com.bdxh.weixiao.configration.security.utils;

import com.bdxh.account.entity.Account;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.userdetail.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

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
}
