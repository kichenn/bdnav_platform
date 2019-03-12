package com.bdxh.app.configration.security.properties;

import java.util.concurrent.TimeUnit;

/**
 * @description: 安全配置属性
 * @author: xuyuan
 * @create: 2019-02-28 16:28
 **/
public interface SecurityConstant {

    /**
     * token自定义数据key
     */
    String DATA_KEY = "data";

    /**
     * token失效时间
     */
    int TOKEN_VALIDITY_SECONDS = (int)TimeUnit.DAYS.toSeconds(1);

    /**
     * refresh_token失效时间
     */
    int REFRESH_TOKEN_VALIDITY_SECONDS = (int)TimeUnit.DAYS.toSeconds(30);

}
