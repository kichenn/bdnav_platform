package com.bdxh.weixiao.configration.security.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.bdxh.weixiao.configration.security.entity.UserInfo;

/**
 * @description: 认证用户上下文
 * @author: xuyuan
 * @create: 2019-03-13 15:40
 **/
public class SecurityContext {

    private static final TransmittableThreadLocal<UserInfo> transmittableThreadLocal = new TransmittableThreadLocal();


    public UserInfo getUserInfo(){
        UserInfo userInfo = transmittableThreadLocal.get();
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        transmittableThreadLocal.set(userInfo);
    }

    public void removeUserInfo(){
        transmittableThreadLocal.remove();
    }

}
