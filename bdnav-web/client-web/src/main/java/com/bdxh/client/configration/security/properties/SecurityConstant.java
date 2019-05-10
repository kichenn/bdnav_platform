package com.bdxh.client.configration.security.properties;

/**
 * @Description: 安全配置属性
 * @Author: Kang
 * @Date: 2019/5/10 14:50
 */
public interface SecurityConstant {


    /**
     * token分割
     */
    String TOKEN_SPLIT = "BearerClient ";

    /**
     * token加密key
     */
    String TOKEN_SIGN_KEY = "bdnav-client";

    /**
     * token过期时间120分钟
     */
    int TOKEN_EXPIRE_TIME = 120;

    /**
     * token刷新时间60分钟
     */
    int TOKEN_REFRESH_TIME = 30;

    /**
     * token请求header
     */
    String TOKEN_REQUEST_HEADER = "Authorization";

    /**
     * token请求param
     */
    String TOKEN_REQUEST_PARAM = "token";

    /**
     * token响应header
     */
    String TOKEN_RESPONSE_HEADER = "Token";


    /**
     * 用户信息参数头
     */
    String USER = "user";
    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * token是否失效前缀
     */
    String TOKEN_IS_REFRESH = "school_token_is_refresh:";

}
