package com.bdxh.backend.configration.security.utils;

import com.bdxh.backend.configration.security.userdetail.MyUserDetails;
import com.bdxh.system.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @description: 用户信息工具类
 * @author: Kang
 * @create: 2019-02-28 16:22
 **/
public class SecurityUtils {

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static User getCurrentUser(){
        User user = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = myUserDetails.getUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getUserName(){
        User user = getCurrentUser();
        String userName = null;
        if (user != null){
            userName = user.getUserName();
        }
        return userName;
    }

    /**
     * 获取当前登录用户真实姓名
     * @return
     */
    public static String getName(){
        User user = getCurrentUser();
        String name = null;
        if (user != null){
            name = user.getRealName();
        }
        return name;
    }

    /**
     * 获取当前登录用户id
     * @return
     */
    public static Long getUserId(){
        User user = getCurrentUser();
        Long userId = null;
        if (user != null){
            userId = user.getId();
        }
        return userId;
    }

}
