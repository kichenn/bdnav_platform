package com.bdxh.common.base.constant;

/**
 * @description: rocketmq常量
 * @author: xuyuan
 * @create: 2019-01-15 11:05
 **/
public class RocketMqConstrants {

    /**
     * rocketmq事务回查redis前缀
     */
    public static final String TRANSACTION_REDIS_PREFIX="rocketmqtransaction:";

    /**
     * rocketmq topics
     */
    public interface Topic {

        /**
         * 钱包充值微信回调topic
         */
        String wechatPayWalletNotice="wechatPayWalletNotice";

        /**
         * 钱包一卡通充值topic
         */
        String xiancardWalletRecharge="xiancardWalletRecharge";

        /**
         * 凯路订单支付topic
         */
        String xiancardWalletKailu="xiancardWalletKailu";

        /**
         * 凯路订单支付更新topic
         */
        String xiancardWalletKailuUpdate="xiancardWalletKailuUpdate";

    }

    /**
     * rocketmq tags
     */
    public interface Tags {

        String wechatPayWalletNotice_app ="app";

        String wechatPayWalletNotice_js ="js";

        String wechatPayWalletNotice_query="query";

        String xiancardWalletRecharge_add="add";

        String xiancardWalletKailu_consumer="consumer";

        String xiancardWalletKailuUpdate_update="update";

    }

}
