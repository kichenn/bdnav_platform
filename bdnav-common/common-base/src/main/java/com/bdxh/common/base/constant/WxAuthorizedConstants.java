package com.bdxh.common.base.constant;

/**
 * 微信授权常量
 */
public class WxAuthorizedConstants {

    public interface Letter {
        /**
         * 公众账号ID
         */
        String appid="wxb5d90dcdscdsfcv316";

        /**
         * 秘钥
         */
        String secret="bcb39fc81escsdcdec65445dscsd2bad8f75415";
        /**
         * 通过code换取access_token地址
         */
        String urlList="https://api.weixin.qq.com/sns/oa";

        /**
         * 授权默认参数url
         */
        String grant_type="authorization_code";

    }
}
