package com.bdxh.client.configration.security.utils;

import com.bdxh.client.configration.security.userdetail.MyUserDetails;
import com.bdxh.school.entity.SchoolUser;
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
    public static SchoolUser getCurrentUser(){
        SchoolUser user = null;
        try {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = myUserDetails.getSchoolUser();
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
        SchoolUser user = getCurrentUser();
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
        SchoolUser user = getCurrentUser();
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
        SchoolUser user = getCurrentUser();
        Long userId = null;
        if (user != null){
            userId = user.getId();
        }
        return userId;
    }

}
