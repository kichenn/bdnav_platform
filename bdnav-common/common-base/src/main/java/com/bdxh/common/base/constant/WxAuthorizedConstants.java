package com.bdxh.common.base.constant;

/**
 * 微信授权常量
 */
public class WxAuthorizedConstants {

    public interface Letter {

        /**
         * 公众账号ID
         */
        String appid="wxb5d9031684f864e4";

        /**
         * 秘钥
         */
        String secret="bcb39fc81ed870ba654452bad8f75415";

        /**
         * 通过code换取access_token地址
         */
        String urlList="https://api.weixin.qq.com/sns/oauth2/access_token";

        /**
         * 授权默认参数url
         */
        String grant_type="authorization_code";

    }

}
