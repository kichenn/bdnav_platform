package com.bdxh.weixiao.configration.security.properties;

import java.util.concurrent.TimeUnit;

/**
 * @description: 安全配置属性
 * @author: xuyuan
 * @create: 2019-02-28 16:28
 **/
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "BearerWeixiao ";

    /**
     * token加密key
     */
    String TOKEN_SIGN_KEY = "bdnav-weixiao";

    /**
     * token时间7天 (转换成分钟)
     */
    int TOKEN_EXPIRE_TIME = (int) TimeUnit.DAYS.toMinutes(7);

    /**
     * token请求header
     */
    String TOKEN_REQUEST_HEADER = "Authorization";

    /**
     * token请求param
     */
    String TOKEN_REQUEST_PARAM = "token";

    /**
     * token存储前缀
     */
    String TOKEN_KEY = "weixiao_token:";

    /**
     * 用户信息参数头
     */
    String USER_INFO = "user_info";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";


    public static void main(String[] args) {
        System.out.println(SecurityConstant.TOKEN_EXPIRE_TIME * 60);
    }
}
