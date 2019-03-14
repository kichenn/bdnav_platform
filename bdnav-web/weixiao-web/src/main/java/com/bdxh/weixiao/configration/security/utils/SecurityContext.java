package com.bdxh.weixiao.configration.security.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.bdxh.weixiao.configration.security.entity.UserInfo;

/**
 * @description: 认证用户上下文
 * @author: xuyuan
 * @create: 2019-03-13 15:40
 **/
public class SecurityContext {

    /**
     * 保存用户信息
     */
    private static final TransmittableThreadLocal<UserInfo> transmittableThreadLocal = new TransmittableThreadLocal();

    public static UserInfo getUserInfo(){
        return transmittableThreadLocal.get();
    }

    public static void setUserInfo(UserInfo userInfo){
        transmittableThreadLocal.set(userInfo);
    }

    public static void removeUserInfo(){
        transmittableThreadLocal.remove();
    }

}
