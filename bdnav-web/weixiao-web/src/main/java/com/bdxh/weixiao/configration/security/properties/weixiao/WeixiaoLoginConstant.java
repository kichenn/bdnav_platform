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
     * 获取微信授权信息
     */
    public static final String WXCODE_URL="https://weixiao.qq.com/apps/school-auth/login";

    /**
     * 获取微信token信息
     */
    public static final String TOKEN_URL = "https://weixiao.qq.com/apps/school-auth/access-token";

    /**
     * 微信token获取用户详情信息
     */
    public static final String USER_INFO_URL = "https://weixiao.qq.com/apps/school-auth/user-info";


    /**
     * appkey
     */
    public static final String appKey = "E0FCA7C96E074060";

    /**
     * appSecret
     */
    public static final String appSecret = "5CF3DCB1A9FCC517C1779C0E69DEF337";
}
