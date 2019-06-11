package com.bdxh.weixiao.configration.security.properties.weixiao;

import lombok.Data;

/**
 * @Description: 微校登录常量信息
 * @Author: Kang
 * @Date: 2019/5/14 17:42
 */
@Data
public class WeixiaoLoginConstant {

    /**
     * 微信授权后重定向的回调链接地址信息(跳转到微校的地址信息)
     */
    public static final String REDIRECT_URI_URL="http://wx-front-prod.bdxht.com/bdnav-school-micro/dist/@address@";


    /**
     * 获取微信授权信息
     */
    public static final String WXCODE_URL="https://weixiao.qq.com/apps/school-auth/login?school_code=@schoolCode@&app_key=@appKey@&redirect_uri=@redirectUri@";

    /**
     * 获取微信token信息
     */
    public static final String TOKEN_URL = "https://weixiao.qq.com/apps/school-auth/access-token";

    /**
     * 微信token获取用户详情信息
     */
    public static final String USER_INFO_URL = "https://weixiao.qq.com/apps/school-auth/user-info";


    /**
     * 服务商：appkey
     */
    public static final String appKey = "846BE2AE98869229";

    /**
     * 服务商：appSecret
     */
    public static final String appSecret = "DA5FCC9BA655CC5BBAC183989B22B757";
}
