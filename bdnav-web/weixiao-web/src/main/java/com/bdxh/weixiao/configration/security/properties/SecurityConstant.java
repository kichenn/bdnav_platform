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
    String TOKEN_SPLIT = "Bearer ";

    /**
     * token加密key
     */
    String TOKEN_SIGN_KEY = "bdnav-platform";

    /**
     * token过期时间7天
     */
    long TOKEN_EXPIRE_TIME = TimeUnit.DAYS.toMillis(7);

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
     * 获取token地址
     */
    String AUTHENTICATION_URL = "/authentication/login";

    /**
     * 服务商key
     */
    String PROVIDER_APP_KEY = "846BE2AE98869229";

    /**
     * 服务商secret
     */
    String PROVIDER_APP_SECRET = "DA5FCC9BA655CC5BBAC183989B22B757";

}
