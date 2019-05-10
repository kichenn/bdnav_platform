package com.bdxh.app.configration.security.properties;

import java.util.concurrent.TimeUnit;

/**
* @Description:  安全属性配置
* @Author: Kang
* @Date: 2019/5/10 15:03
*/
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "BearerApp ";

    /**
     * token加密key
     */
    String TOKEN_SIGN_KEY = "bdnav-app";

    /**
     * token过期时间14天
     */
    long TOKEN_EXPIRE_TIME = TimeUnit.DAYS.toMillis(14);

    /**
     * token刷新时间7天
     */
    long TOKEN_REFRESH_TIME = TimeUnit.DAYS.toMillis(7);

    /**
     * token请求header
     */
    String TOKEN_REQUEST_HEADER = "Authorization";

    /**
     * token响应header
     */
    String TOKEN_RESPONSE_HEADER = "Token";

    /**
     * 用户信息参数头
     */
    String ACCOUNT = "account";

    /**
     * token是否失效前缀
     */
    String TOKEN_IS_REFRESH = "account_token_is_refresh:";

    /**
     * token是否失效前缀
     */
    String TOKEN_KEY = "account_token:";

}
