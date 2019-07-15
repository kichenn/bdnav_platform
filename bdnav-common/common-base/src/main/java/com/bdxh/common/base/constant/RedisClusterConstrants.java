package com.bdxh.common.base.constant;

/**
 * @Description: redis常量配置
 * @Author: Kang
 * @Date: 2019/6/20 14:57
 */
public class RedisClusterConstrants {

    /**
     * String类型key前缀
     */
    public interface KeyPrefix {

        String wechatpay_wallet_app_notice = "wechatpaywalletappnotice:";

        /**
         * 家长端，服务购买key
         */
        String wechatpay_wallet_js_notice = "wechatpaywalletjsnotice:";

        /**
         * 钱包充值key
         */
        String wallet_recharge_js_notice = "walletrechargejsnotice:";
    }

}
