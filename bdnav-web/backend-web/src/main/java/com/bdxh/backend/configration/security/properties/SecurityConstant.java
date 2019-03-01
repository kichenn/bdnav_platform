package com.bdxh.backend.configration.security.properties;

/**
 * @description: 安全配置属性
 * @author: xuyuan
 * @create: 2019-02-28 16:28
 **/
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * token加密key
     */
    String TOKEN_SIGN_KEY = "bdnav-platform";

    /**
     * token过期时间60分钟
     */
    int TOKEN_EXPIRE_TIME = 60;

    /**
     * token请求header
     */
    String TOKEN_HEADER = "Authorization";


    /**
     * 用户信息参数头
     */
    String USER = "user";
    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

}
